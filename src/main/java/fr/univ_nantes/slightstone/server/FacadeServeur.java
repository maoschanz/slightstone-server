package fr.univ_nantes.slightstone.server;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fr.univ_nantes.slightstone.controller.Controleur;
import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.Joueur;

@Controller
public class FacadeServeur implements IServeur{

	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	Logger logger = LoggerFactory.getLogger(FacadeServeur.class);
	
	Semaphore mutexFileAttente = new Semaphore(1);
	private LinkedList<Joueur> fileAttente = new LinkedList<Joueur>();
	
	private HashMap<String, String> socketsId = new HashMap<String, String>();
	private HashMap<String, Controleur> jeux = new HashMap<String, Controleur>();
	
	
	public FacadeServeur () {
		//TODO
	}
	
	private Joueur rechercheAdversaire() throws InterruptedException {
		this.mutexFileAttente.acquire();
		Joueur adversaire;
		if(this.fileAttente.isEmpty()) {
			adversaire =  null;
		} else {
			adversaire = this.fileAttente.removeFirst();
		}
		this.mutexFileAttente.release();
		return adversaire;
	}
	
	@MessageMapping("/partie")
	//@SendTo("/topic/greetings")
	public void lancerPartie(Principal principal, MessageLancerPartie message) {
		logger.warn(message.getPseudo());
		logger.warn(principal.getName());
		logger.warn(message.getHeros().toString());
		
		try {
			Joueur joueur = new Joueur(message.getPseudo(), message.getHeros());
			messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", joueur);
			logger.warn(joueur.getPseudo());
			Joueur adversaire = this.rechercheAdversaire();
			if(adversaire != null) {
				Jeu jeu = new Jeu(joueur, adversaire);
				Controleur controleur = new Controleur(jeu);
				this.jeux.put(joueur.getPseudo(), controleur);
				this.jeux.put(adversaire.getPseudo(), controleur);
				//envoyer état du jeu
				//return "coucou";
			} else {
				//joueur en attente
				//return "attente";
			}
		} catch (Exception e) {
			//return "erreur";
		}
	}
	
	

		/* avant d'envoyer, on compare les joueur.get_board au plateau stocké et le joueur.get_hand à la main stockée */

	public void jouerCarte(Integer idCarte){

	}

	public void jouerCarte(Integer idCarte, Integer idCible){

	}

	public void terminerTour(){

	}

	public void lancerActionHeros(){

	}

	public void lancerActionHeros(Integer idCible){

	}

	public void attaquer(Integer idServiteur, Integer idCible){

	}
}