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

import fr.univ_nantes.slightstone.controller.ViolationReglesException;
import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.Joueur;
import fr.univ_nantes.slightstone.server.messages.*;

@Controller
public class FacadeServeur {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	Logger logger = LoggerFactory.getLogger(FacadeServeur.class);

	Semaphore mutexFileAttente = new Semaphore(1);
	private LinkedList<Joueur> fileAttente = new LinkedList<Joueur>();

	private HashMap<Joueur, String> convJoueurId = new HashMap<Joueur, String>();
	private HashMap<String, Joueur> convIdJoueur = new HashMap<String, Joueur>();
	private HashMap<String, Partie> parties = new HashMap<String, Partie>();

	private Joueur creerJoueur(String id, String pseudo, ClasseHeros heros) {
		logger.warn("--> Création d'un nouveau joueur");
		Joueur joueur = new Joueur(pseudo, heros);
		this.convIdJoueur.put(id, joueur);
		this.convJoueurId.put(joueur, id);
		logger.warn(String.format("==> Joueur %s crée", joueur.getPseudo()));
		return joueur;
	}
	
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

	private void mettreJoueurEnAttente(Joueur joueur) {
		logger.warn("--> Mettre joueur en attente");
		try {
			this.mutexFileAttente.acquire();
			this.fileAttente.addLast(joueur);
			this.mutexFileAttente.release();
			
			Message message = new Message("En attente d'un autre joueur...");
			this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur), "/queue/attente", message);
			logger.warn(String.format("==> Joueur %s en attente", joueur.getPseudo()));
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			MessageErreur erreur = new MessageErreur(501, "Une erreur interne est survenue");
			this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur), "/queue/erreur", erreur);
		}
	}

	private void nouvellePartie(Joueur joueur, Joueur adversaire) {
		logger.warn("--> Création d'une nouvelle partie");
		Partie partie = new Partie(joueur, adversaire);
		this.parties.put(this.convJoueurId.get(joueur), partie);
		this.parties.put(this.convJoueurId.get(adversaire), partie);
		this.envoyerEtatPartie(partie, "/queue/debutPartie");
		logger.warn(String.format("==> Nouvelle partie créée avec les joueurs %s et %s", joueur.getPseudo(), adversaire.getPseudo()));
	}
	
	private void envoyerEtatPartie(Partie partie, String destination) {
		Joueur joueur1 = partie.getJoueur1();
		MessageEtatPartie etatPartieJoueur1 = new MessageEtatPartie(partie.getEtatPartie(joueur1));
		this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur1), destination, etatPartieJoueur1);
		Joueur joueur2 = partie.getJoueur2();
		MessageEtatPartie etatPartieJoueur2 = new MessageEtatPartie(partie.getEtatPartie(joueur2));
		this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur2), destination, etatPartieJoueur2);
	}
	
	private void envoyerResultatPartie(Partie partie, String pseudoVainqueur) {
		MessageFinPartie message = new MessageFinPartie(pseudoVainqueur);
		Joueur joueur1 = partie.getJoueur1();
		Joueur joueur2 = partie.getJoueur1();
		this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur1), "/queue/finPartie", message);
		this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(joueur2), "/queue/finPartie", message);
	}
	
	private void libererRessourcesJoueur(Joueur joueur) {
		String idJoueur = this.convJoueurId.get(joueur);
		this.convJoueurId.remove(joueur);
		this.convIdJoueur.remove(idJoueur);
		this.parties.remove(idJoueur);
	}

	private void libererRessourcesPartie(Partie partie) {
		Joueur joueur1 = partie.getJoueur1();
		this.libererRessourcesJoueur(joueur1);
		Joueur joueur2 = partie.getJoueur2();
		this.libererRessourcesJoueur(joueur2);
	}

	private void actualiserJoueurs(Partie partie) {
		if (partie.estTerminee()) {
			String pseudoVainqueur = partie.getVainqueur().getPseudo();
			this.envoyerResultatPartie(partie, pseudoVainqueur);
			this.libererRessourcesPartie(partie);
		} else {
			this.envoyerEtatPartie(partie, "/queue/etatPartie");
		}
	}

	private void erreurJoueurOuPartieInexistante(Principal principal) {
		MessageErreur erreur = new MessageErreur(403,
				"Le joueur n'existe pas où n'est actuellement dans aucune partie");
		logger.warn(String.format("-- %s", erreur.getMessage()));
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	private void erreurIdentifiantInvalide(Principal principal) {
		MessageErreur erreur = new MessageErreur(401, "Un ou plusieurs identifiant(s) sont invalides !");
		logger.warn(String.format("-- %s", erreur.getMessage()));
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	private void erreurViolationReglesJeu(Principal principal, String regle) {
		String message = String.format("Violation d'une règle du jeu : %s", regle);
		MessageErreur erreur = new MessageErreur(401, message);
		this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
	}

	@MessageMapping("/nouvellePartie")
	public void nouvellePartie(Principal principal, MessageNouvellePartie message) {
		logger.warn("--> Nouvelle partie");
		Joueur joueur = this.convIdJoueur.get(principal.getName());
		if(joueur != null) {
			MessageErreur erreur = new MessageErreur(400, "Vous êtes déjà en attente d'une partie...");
			this.messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/erreur", erreur);
			return;
		}
		try {
			joueur = this.creerJoueur(principal.getName(), message.getPseudo(), message.getHeros());
			Joueur adversaire = this.rechercheAdversaire();
			if (adversaire != null) {
				this.nouvellePartie(joueur, adversaire);
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
		Joueur joueur = this.convIdJoueur.get(principal.getName());
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
		Joueur joueur = this.convIdJoueur.get(principal.getName());
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
		Joueur joueur = this.convIdJoueur.get(principal.getName());
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

	@MessageMapping("/lancerActionSpecial")
	public void lancerActionHeros(Principal principal, MessageLancerActionSpecial message) {
		Joueur joueur = this.convIdJoueur.get(principal.getName());
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
		Joueur joueur = this.convIdJoueur.get(principal.getName());
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
		Partie partie = this.parties.get(this.convJoueurId.get(joueur));
		if(partie != null) {
			logger.warn("le joueur est dans une partie");
			Joueur adversaire = partie.getJoueur1().equals(joueur) ? partie.getJoueur2() : partie.getJoueur1();
			Message message = new Message("Votre adversaire a quitté le combat !");
			this.messagingTemplate.convertAndSendToUser(this.convJoueurId.get(adversaire), "/queue/abandon", message);
			this.libererRessourcesPartie(partie);
		} else {
			this.libererRessourcesJoueur(joueur);
		}
	}
	
	@EventListener
	public void onDisconnectEvent(SessionDisconnectEvent event) {
		String idSocket = event.getUser().getName();
		Joueur joueur = this.convIdJoueur.get(idSocket);
		if(joueur != null) {
			this.quitterJeu(joueur);
		} else {
			logger.warn("le joueur n'avait encore lancé aucune partie");
		}
		logger.warn("Client with username {} disconnected", idSocket);
	}
}
