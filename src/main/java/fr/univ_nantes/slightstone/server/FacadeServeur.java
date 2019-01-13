package fr.univ_nantes.slightstone.server;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.Joueur;
import fr.univ_nantes.slightstone.model.exceptions.ViolationReglesException;
import fr.univ_nantes.slightstone.server.messages.*;

/**
 * Cette classe a pour rôle de traiter les demandes des clients via WebSocket
 * Les canaux de communication avec la façade sont les suivants :
 * 
 * 		- /app/nouvellePartie : le client souhaite créer une nouvelle partie
 * 		- /app/jouerCarteSort : le client souhaite jouer une carte sort
 * 		- /app/jouerCarteServiteur : le client souhaite jouer une carte serviteur
 * 		- /app/lancerActionSpeciale : le client souhaite lancer l'action spécial de son héros
 * 		- /app/attaquer : le client souhaite attaquer une cible avec un de ces serviteurs
 * 		- /app/terminerTour : le client souhaite mettre fin à son tour
 * 
 * Le serveur utilisera les canaux de communication ci-dessous pour envoyer des messages au client
 * (le client devra donc souscrire à ces canaux) :
 * 
 * 		- /user/queue/attente : le client est en attente d'un autre joueur. Dès qu'un autre joueur se
 * 								présentera, la partie commencera
 * 		- /user/queue/debutPartie : la partie commence, le serveur envoie l'état de la partie
 * 		- /user/queue/etatPartie : le modèle a changé, le client doit actualiser l'affichage, le serveur envoie l'état de la partie
 * 		- /user/queue/nouveauTour : c'est au tour de l'autre joueur, le serveur envoie l'état de la partie
 * 		- /user/queue/finPartie : la partie est terminée, le serveur envoie le pseudo du vainqueur
 * 		- /user/queue/erreur : une erreur est survenue (est censée servir que lors de la phase de développement)
 */
@Controller
public class FacadeServeur {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	Logger logger = LoggerFactory.getLogger(FacadeServeur.class);

	Semaphore mutexFileAttente = new Semaphore(1); // évite les problèmes de concurrence liée à un accès concurrent
												   // à cette ressource.
	private LinkedList<Joueur> fileAttente = new LinkedList<Joueur>(); // liste des joueurs en attente d'une partie.

	private Mappeur<String, Joueur> mappeurJoueurId = new Mappeur<String, Joueur>(); // liste des joueurs en jeu ou dans la file d'attente
																					 // permet de faire le lien entre un client (via l'identifiant de sa socket) 
																					 // et l'objet Joueur qui le représente dans le jeu.
	private HashMap<String, Partie> parties = new HashMap<String, Partie>(); // liste des parties en cours.
																			 // la clé de cet HashMap correspond à l'identifiant d'une socket
																			 // afin de faire le lien entre un client et la partie dans laquelle
																			 // il joue.

	/**
	 * Crée un nouveau joueur et fait le lien entre l'objet Joueur et l'idenfiant de
	 * la socket du client
	 * 
	 * @param id : identifiant de la socket du client
	 * @param pseudo : pseudo du joueur
	 * @param heros : classe à laquelle appartiendra le héros du joueur
	 * @return
	 */
	private Joueur creerJoueur(String id, String pseudo, ClasseHeros heros) {
		logger.warn("--> Création d'un nouveau joueur");
		Joueur joueur = new Joueur(pseudo, heros);
		this.mappeurJoueurId.put(id, joueur);
		logger.warn(String.format("==> Joueur %s crée", joueur.getPseudo()));
		return joueur;
	}
	
	/**
	 * Recherche un adversaire dans la file d'attente afin de débuter une
	 * nouvelle partie
	 * 
	 * @return : un autre joueur ou null si la file d'attente est vide
	 * @throws InterruptedException
	 */
	private Joueur rechercheAdversaire() throws InterruptedException {
		logger.warn("--> Recherche d'un adversaire !");
		Joueur adversaire;
		this.mutexFileAttente.acquire();
		if (this.fileAttente.isEmpty()) {
			adversaire = null;
			logger.warn("Aucun adversaire trouvé...");
		} else {
			adversaire = this.fileAttente.removeFirst();
			logger.warn(String.format("==> Adversaire trouvé : %s", adversaire.getPseudo()));
		}
		this.mutexFileAttente.release();
		return adversaire;
	}

	/**
	 * Ajoute le joueur dans la file d'attente
	 * 
	 * @param joueur : joueur à ajouter dans la file d'attente
	 */
	private void mettreJoueurEnAttente(Joueur joueur) {
		logger.warn("--> Mettre joueur en attente");
		try {
			this.mutexFileAttente.acquire();
			this.fileAttente.addLast(joueur);
			this.mutexFileAttente.release();
			
			Message message = new Message("En attente d'un autre joueur...");
			this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur), "/queue/attente", message);
			logger.warn(String.format("==> Joueur %s en attente", joueur.getPseudo()));
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			MessageErreur erreur = new MessageErreur(501, "Une erreur interne est survenue");
			this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur), "/queue/erreur", erreur);
		}
	}

	/**
	 * Crée une nouvelle partie avec les joueurs joueur1 et joueur2.
	 * Etablit un lien entre la partie et l'identifiant de la socket
	 * de chaque joueur.
	 * Ce lien permettre de déterminer dans quelle partie joue un joueur
	 * lorsque le serveur recevra une requête.
	 * 
	 * @param joueur1
	 * @param joueur2
	 */
	private void creerNouvellePartie(Joueur joueur1, Joueur joueur2) {
		logger.warn("--> Création d'une nouvelle partie");
		Partie partie = new Partie(joueur1, joueur2);
		this.parties.put(this.mappeurJoueurId.getClef(joueur1), partie);
		this.parties.put(this.mappeurJoueurId.getClef(joueur2), partie);
		this.envoyerEtatPartie(partie, "/queue/debutPartie");
		logger.warn(String.format("==> Nouvelle partie créée avec les joueurs %s et %s", joueur1.getPseudo(), joueur2.getPseudo()));
	}
	
	/**
	 * Envoie l'état de la partie aux deux joueurs d'une partie
	 * 
	 * @param partie : partie concernée
	 * @param destination :
	 * 			- /queue/debutPartie => lorsque la partie commence
	 * 		 	- /queue/etatPartie => après toutes actions ayant pu modifier le modèle
	 * 			- /queue/nouveauTour => lorsqu'un nouveau tour commence
	 */
	private void envoyerEtatPartie(Partie partie, String destination) {
		Joueur joueur1 = partie.getJoueur1();
		MessageEtatPartie etatPartieJoueur1 = new MessageEtatPartie(partie.getEtatPartie(joueur1));
		this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur1), destination, etatPartieJoueur1);
		Joueur joueur2 = partie.getJoueur2();
		MessageEtatPartie etatPartieJoueur2 = new MessageEtatPartie(partie.getEtatPartie(joueur2));
		this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur2), destination, etatPartieJoueur2);
	}
	
	/**
	 * Envoie le pseudo du vainqueur aux deux joueurs d'une partie
	 * 
	 * @param partie
	 */
	private void envoyerResultatPartie(Partie partie) {
		try {
			String pseudoVainqueur = partie.getVainqueur().getPseudo();
			MessageFinPartie message = new MessageFinPartie(pseudoVainqueur);
			Joueur joueur1 = partie.getJoueur1();
			Joueur joueur2 = partie.getJoueur1();
			this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur1), "/queue/finPartie", message);
			this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(joueur2), "/queue/finPartie", message);
		} catch (ViolationReglesException e) {
			e.printStackTrace();
		}
	}
	
	private void libererRessourcesJoueur(Joueur joueur) {
		String idJoueur = this.mappeurJoueurId.getClef(joueur);
		this.mappeurJoueurId.removeViaClef(idJoueur);
		this.parties.remove(idJoueur);
	}

	private void libererRessourcesPartie(Partie partie) {
		Joueur joueur1 = partie.getJoueur1();
		this.libererRessourcesJoueur(joueur1);
		Joueur joueur2 = partie.getJoueur2();
		this.libererRessourcesJoueur(joueur2);
	}

	/**
	 * Envoie l'état de la partie aux deux joueurs d'une partie si cette dernière n'est
	 * pas terminée.
	 * Envoie le pseudo du vainqueur aux deux joueurs d'une partie si cette dernière
	 * est terminée.
	 * 
	 * @param partie
	 */
	private void actualiserJoueurs(Partie partie) {
		if (partie.estTerminee()) {
			this.envoyerResultatPartie(partie);
			this.libererRessourcesPartie(partie);
		} else {
			this.envoyerEtatPartie(partie, "/queue/etatPartie");
		}
	}
	
	/**
	 * Envoie un message d'erreur au client l'informant qu'il n'a pas accès à cette
	 * fonctionnalité car il est actuellement dans aucune partie ou n'est pas
	 * encore enregistré auprès du serveur.
	 * 
	 * @param principal
	 */
	private void erreurJoueurOuPartieInexistante(Principal principal) {
		MessageErreur erreur = new MessageErreur(403,
				"Le joueur n'existe pas où est actuellement dans aucune partie");
		logger.warn(String.format("-- %s", erreur.getMessage()));
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	/**
	 * Envoie un message d'erreur au client l'informant que l'identifiant donnée est invalide.
	 * 
	 * @param principal
	 */
	private void erreurIdentifiantInvalide(Principal principal) {
		MessageErreur erreur = new MessageErreur(401, "Un ou plusieurs identifiant(s) sont invalides !");
		logger.warn(String.format("-- %s", erreur.getMessage()));
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	/**
	 * Envoie un message d'erreur au client l'informant qu'il vient d'essayer de violer une des règles
	 * du jeu.
	 * 
	 * @param principal
	 * @param regle
	 */
	private void erreurViolationReglesJeu(Principal principal, String regle) {
		String message = String.format("Violation d'une règle du jeu : %s", regle);
		MessageErreur erreur = new MessageErreur(401, message);
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	
	@MessageMapping("/nouvellePartie")
	public void nouvellePartie(Principal principal, MessageNouvellePartie message) {
		logger.warn("--> Nouvelle partie");
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		if(joueur != null) {
			MessageErreur erreur = new MessageErreur(400, "Vous êtes déjà en attente d'une partie...");
			this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
			return;
		}
		try {
			joueur = this.creerJoueur(principal.getName(), message.getPseudo(), message.getHeros());
			Joueur adversaire = this.rechercheAdversaire();
			if (adversaire != null) {
				this.creerNouvellePartie(joueur, adversaire);
			} else {
				this.mettreJoueurEnAttente(joueur);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			MessageErreur erreur = new MessageErreur(501, "Une erreur interne est survenue");
			this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
		}
	}
	
	@MessageMapping("/jouerCarteServiteur")
	public void jouerCarteServiteur(Principal principal, MessageJouerCarteServiteur message) {
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		Partie partie = this.parties.get(principal.getName());
		if (joueur != null && partie != null) {
			logger.warn(String.format("--> %s joue une carte serviteur", joueur.getPseudo()));
			try {
				partie.jouerCarte(joueur, message.getIdCarte());
				logger.warn("==> carte serviteur joué !");
				this.actualiserJoueurs(partie);
			} catch (IdentifiantInvalideException e) {
				this.erreurIdentifiantInvalide(principal);
			} catch (ViolationReglesException e) {
				this.erreurViolationReglesJeu(principal, e.getMessage());
			}
		} else {
			this.erreurJoueurOuPartieInexistante(principal);
		}
	}

	@MessageMapping("/jouerCarteSort")
	public void jouerCarteSort(Principal principal, MessageJouerCarteSort message) {
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		Partie partie = this.parties.get(principal.getName());
		if (joueur != null && partie != null) {
			logger.warn(String.format("--> %s joue une carte sort", joueur.getPseudo()));
			Integer idCarte = message.getIdCarte();
			Integer idCible = message.getIdCible();
			try {
				if (idCible == null) {
					logger.warn("++ aucune cible !");
					partie.jouerCarte(joueur, idCarte);
				} else {
					logger.warn(String.format("++ cible : %d !", idCible));
					partie.jouerCarteSort(joueur, idCarte, idCible);
				}
				logger.warn("==> carte sort joué !");
				this.actualiserJoueurs(partie);
			} catch (IdentifiantInvalideException e) {
				logger.warn(e.getMessage());
				this.erreurIdentifiantInvalide(principal);
			} catch (ViolationReglesException e) {
				logger.warn(e.getMessage());
				this.erreurViolationReglesJeu(principal, e.getMessage());
			}
		} else {
			this.erreurJoueurOuPartieInexistante(principal);
		}
	}

	@MessageMapping("/terminerTour")
	public void terminerTour(Principal principal) {
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		Partie partie = this.parties.get(principal.getName());
		if (joueur != null && partie != null) {
			logger.warn(String.format("--> Le joueur %s met fin à son tour", joueur.getPseudo()));
			try {
				partie.terminerTour(joueur);
				this.envoyerEtatPartie(partie, "/queue/nouveauTour");
			} catch (ViolationReglesException e) {
				logger.warn(e.getMessage());
				this.erreurViolationReglesJeu(principal, e.getMessage());
			}
			logger.warn("==> nouveau tour");
		} else {
			this.erreurJoueurOuPartieInexistante(principal);
		}
	}

	@MessageMapping("/lancerActionSpeciale")
	public void lancerActionHeros(Principal principal, MessageLancerActionSpecial message) {
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		Partie partie = this.parties.get(principal.getName());
		if (joueur != null && partie != null) {
			logger.warn(String.format("--> Le joueur %s lance son attaque spéciale", joueur.getPseudo()));
			Integer idCible = message.getIdCible();
			try {
				if (idCible == null) {
					partie.lancerActionHeros(joueur);
				} else {
					partie.lancerActionHeros(joueur, idCible);
				}
				logger.warn(String.format("==> Le joueur %s a lancé son attaque spéciale", joueur.getPseudo()));
				this.actualiserJoueurs(partie);
			} catch (IdentifiantInvalideException e) {
				this.erreurIdentifiantInvalide(principal);
			} catch (ViolationReglesException e) {
				logger.warn(e.getMessage());
				this.erreurViolationReglesJeu(principal, e.getMessage());
			}
		} else {
			this.erreurJoueurOuPartieInexistante(principal);
		}
	}

	@MessageMapping("/attaquer")
	public void attaquer(Principal principal, MessageAttaquer message){
		Joueur joueur = this.mappeurJoueurId.getValeur(principal.getName());
		Partie partie = this.parties.get(principal.getName());
		if(joueur != null && partie != null) {
			logger.warn(String.format("--> Le joueur %s attaque avec un serviteur", joueur.getPseudo()));
			Integer idServiteur = message.getIdServiteur();
			Integer idCible = message.getIdCible();
			logger.warn(String.format("++ le joueur attaque avec le serviteur %d la cible %d", idServiteur, idCible));
			try {
				partie.attaquer(joueur, idServiteur, idCible);
				logger.warn(String.format("==> Le joueur %s a attaqué avec un serviteur", joueur.getPseudo()));
				this.actualiserJoueurs(partie);
			} catch (IdentifiantInvalideException e) {
				this.erreurIdentifiantInvalide(principal);
			} catch (ViolationReglesException e) {
				logger.warn(e.getMessage());
				this.erreurViolationReglesJeu(principal, e.getMessage());
			}
		} else {
			this.erreurJoueurOuPartieInexistante(principal);
		}
	}
	
	private void retirerJoueurFileAttenteSiExiste(Joueur joueur) {
		try {
			this.mutexFileAttente.acquire();
			if(this.fileAttente.contains(joueur)) {
				logger.warn("le joueur est dans la file d'attente");
				this.fileAttente.remove(joueur);
				logger.warn("le joueur a été retiré de la file d'attente : " + (!this.fileAttente.contains(joueur)));
			}
			this.mutexFileAttente.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private void quitterJeu(Joueur joueur) {
		this.retirerJoueurFileAttenteSiExiste(joueur);
		Partie partie = this.parties.get(this.mappeurJoueurId.getClef(joueur));
		if(partie != null) {
			logger.warn("le joueur est dans une partie");
			Joueur adversaire = partie.getJoueur1().equals(joueur) ? partie.getJoueur2() : partie.getJoueur1();
			Message message = new Message("Votre adversaire a quitté le combat !");
			this.messagingTemplate.convertAndSendToUser(this.mappeurJoueurId.getClef(adversaire), "/queue/abandon", message);
			this.libererRessourcesPartie(partie);
		} else {
			this.libererRessourcesJoueur(joueur);
		}
	}
	
	@EventListener
	public void onDisconnectEvent(SessionDisconnectEvent event) {
		String idSocket = event.getUser().getName();
		Joueur joueur = this.mappeurJoueurId.getValeur(idSocket);
		if(joueur != null) {
			this.quitterJeu(joueur);
		} else {
			logger.warn("le joueur n'avait encore lancé aucune partie");
		}
		logger.warn("Client with username {} disconnected", idSocket);
	}
}
