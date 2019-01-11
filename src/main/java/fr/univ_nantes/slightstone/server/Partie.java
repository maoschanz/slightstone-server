package fr.univ_nantes.slightstone.server;

import java.util.HashMap;
import java.util.Random;

import fr.univ_nantes.slightstone.controller.Controleur;
import fr.univ_nantes.slightstone.controller.ViolationReglesException;
import fr.univ_nantes.slightstone.model.CarteServiteur;
import fr.univ_nantes.slightstone.model.Ciblable;
import fr.univ_nantes.slightstone.model.DescripteurCarte;
import fr.univ_nantes.slightstone.model.DescripteurSort;
import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.Joueur;

public class Partie {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private Controleur controleurJeu;
	private EtatPartie generateurEtatPartie;
	
	private Random generateurIdentifiant = new Random();
	
	private HashMap<Integer, DescripteurCarte> identifiantsVersCartes = new HashMap<Integer, DescripteurCarte>();
	private HashMap<DescripteurCarte, Integer> cartesVersIdentifiants = new HashMap<DescripteurCarte, Integer>();
	private HashMap<Integer, Ciblable> identifiantsVersCiblables = new HashMap<Integer, Ciblable>();
	private HashMap<Ciblable, Integer> ciblablesVersIdentifants = new HashMap<Ciblable, Integer>();

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public Partie(Joueur joueur1, Joueur joueur2) {
		Jeu jeu = new Jeu(joueur1, joueur2);
		jeu.initialiserMainJoueurs();
		this.controleurJeu = new Controleur(jeu);
		this.generateurEtatPartie = new EtatPartie(jeu, this);
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	public Joueur getJoueur1() {
		return this.controleurJeu.getJoueur1();
	}
	
	public Joueur getJoueur2() {
		return this.controleurJeu.getJoueur2();
	}
	
	public boolean estTerminee() {
		return this.controleurJeu.estJeuTermine();
	}
	
	public Joueur getVainqueur() throws ViolationReglesException {
		return this.controleurJeu.getVainqueur();
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	public HashMap<String, Object> getEtatPartie(Joueur joueur) {
		return this.generateurEtatPartie.recupererEtatPartie(joueur);
	}

	/**
	 * Ajoute une cible à la partie
	 * 
	 * @param cible : cible à identifier
	 * @return identifiant : l'identifiant produit pour la cible
	 */
	private int ajouterNouvelleCible(Ciblable cible) {
		int identifiant = this.generateurIdentifiant.nextInt();
		this.identifiantsVersCiblables.put(identifiant, cible);
		this.ciblablesVersIdentifants.put(cible, identifiant);
		return identifiant;
	}
	
	/**
	 * Identifie une cible dans la partie
	 * 
	 * @param cible : cible à identifier
	 * @return identifiant : l'identifiant de la cible
	 */
	public Integer getIdentifiantCible(Ciblable cible) {
		Integer identifiant = this.ciblablesVersIdentifants.get(cible);
		if(identifiant == null) {
			return this.ajouterNouvelleCible(cible);
		} else {
			return identifiant;
		}
	}
	
	/**
	 * Ajoute une carte à la partie
	 * 
	 * @param carte : carte à identifier
	 * @return identifiant : l'identifiant produit pour la carte
	 */
	private int ajouterNouvelleCarte(DescripteurCarte carte) {
		int identifiant = this.generateurIdentifiant.nextInt();
		this.identifiantsVersCartes.put(identifiant, carte);
		this.cartesVersIdentifiants.put(carte, identifiant);
		return identifiant;
	}
	
	/**
	 * Identifie une carte dans la partie
	 * 
	 * @param carte : carte à identifier
	 * @return identifiant : l'identifiant de la carte
	 */
	public Integer getIdentifiantCarte(DescripteurCarte carte) {
		Integer identifiant = this.cartesVersIdentifiants.get(carte);
		if(identifiant == null) {
			return this.ajouterNouvelleCarte(carte);
		} else {
			return identifiant;
		}
	}
	
	public void jouerCarte(Joueur joueur, Integer idCarte) throws IdentifiantInvalideException, ViolationReglesException {
		DescripteurCarte carte = this.identifiantsVersCartes.get(idCarte);
		if(carte == null) {
			throw new IdentifiantInvalideException();
		}
		this.controleurJeu.jouerCarte(joueur, carte);
	}
	
	public void jouerCarteSort(Joueur joueur, Integer idCarte, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		DescripteurCarte carte = this.identifiantsVersCartes.get(idCarte);
		Ciblable cible = this.identifiantsVersCiblables.get(idCible);
		if(carte == null || cible == null) {
			throw new IdentifiantInvalideException();
		}
		if(carte instanceof DescripteurSort) {	//TODO: laisser faire le controleur !
			this.controleurJeu.jouerCarteSort(joueur, (DescripteurSort) carte, cible);
		}
	}
	
	public void terminerTour(Joueur joueur) throws ViolationReglesException {
		this.controleurJeu.terminerTour(joueur);
	}

	public void lancerActionHeros(Joueur joueur) throws ViolationReglesException {
		this.controleurJeu.lancerActionHeros(joueur);
	}

	public void lancerActionHeros(Joueur joueur, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		Ciblable cible = this.identifiantsVersCiblables.get(idCible);
		if(cible == null) {
			throw new IdentifiantInvalideException();
		}
		this.controleurJeu.lancerActionHeros(joueur, cible);
	}

	public void attaquer(Joueur joueur, Integer idServiteur, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		Ciblable serviteur = this.identifiantsVersCiblables.get(idServiteur);
		Ciblable cible = this.identifiantsVersCiblables.get(idCible);
		if(serviteur == null || cible == null) {
			throw new IdentifiantInvalideException();
		}
		if(serviteur instanceof CarteServiteur) { //TODO: laisser faire le controleur
			this.controleurJeu.attaquer(joueur, (CarteServiteur) serviteur, cible);
		}
	}
}
