package fr.univ_nantes.slightstone.server;

import java.util.HashMap;
import java.util.Random;

import fr.univ_nantes.slightstone.model.CarteServiteur;
import fr.univ_nantes.slightstone.model.Ciblable;
import fr.univ_nantes.slightstone.model.InterfaceModele;
import fr.univ_nantes.slightstone.model.DescripteurCarte;
import fr.univ_nantes.slightstone.model.DescripteurSort;
import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.Joueur;
import fr.univ_nantes.slightstone.model.exceptions.ViolationReglesException;

/**
 * Cette classe a pour rôle de gérer une partie entre deux joueurs.
 * Elle fait le lien entre les informations envoyées aux clients et
 * les objets java du modèle à l'aide d'identifiants.
 */
public class Partie {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private InterfaceModele modele;
	private EtatPartie generateurEtatPartie;
	
	private Random generateurIdentifiant = new Random(); // sert à générer un identifiant unique pour chaque objet du modèle
	
	private Mappeur<Integer, DescripteurCarte> mappeurIdentifiantsCartes; // fait le lien entre les identifiants envoyés aux clients
																		  // et les objets java DescripteurCarte
																		  // permet au client de désigner/manipuler une carte via un identifiant
	private Mappeur<Integer, Ciblable> mappeurIdentifiantsCiblables; // fait le lien entre les identifiants envoyés aux clients
																	 // et les objets java Ciblable
																	 // permet au client de désigner/manipuler un ciblable (héros ou serviteur) 
																	 // via un identifiant
	
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public Partie(Joueur joueur1, Joueur joueur2) {
		Jeu jeu = new Jeu(joueur1, joueur2);
		this.modele = new InterfaceModele(jeu);
		this.generateurEtatPartie = new EtatPartie(this.modele, this);
		this.mappeurIdentifiantsCartes = new Mappeur<Integer, DescripteurCarte>();
		this.mappeurIdentifiantsCiblables = new Mappeur<Integer, Ciblable>();
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	public Joueur getJoueur1() {
		return this.modele.getJeu().getJoueur1();
	}
	
	public Joueur getJoueur2() {
		return this.modele.getJeu().getJoueur2();
	}
	
	public boolean estTerminee() {
		return this.modele.getJeu().estTermine();
	}
	
	public Joueur getVainqueur() throws ViolationReglesException {
		return this.modele.getVainqueur();
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
		this.mappeurIdentifiantsCiblables.put(identifiant, cible);
		return identifiant;
	}
	
	/**
	 * Identifie une cible dans la partie
	 * 
	 * @param cible : cible à identifier
	 * @return identifiant : l'identifiant de la cible
	 */
	public Integer getIdentifiantCible(Ciblable cible) {
		Integer identifiant = this.mappeurIdentifiantsCiblables.getClef(cible);
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
		this.mappeurIdentifiantsCartes.put(identifiant, carte);
		return identifiant;
	}
	
	/**
	 * Identifie une carte dans la partie
	 * 
	 * @param carte : carte à identifier
	 * @return identifiant : l'identifiant de la carte
	 */
	public Integer getIdentifiantCarte(DescripteurCarte carte) {
		Integer identifiant = this.mappeurIdentifiantsCartes.getClef(carte);
		if(identifiant == null) {
			return this.ajouterNouvelleCarte(carte);
		} else {
			return identifiant;
		}
	}
	
	public void jouerCarte(Joueur joueur, Integer idCarte) throws IdentifiantInvalideException, ViolationReglesException {
		DescripteurCarte carte = this.mappeurIdentifiantsCartes.getValeur(idCarte);
		if(carte == null) {
			throw new IdentifiantInvalideException();
		}
		this.modele.jouerCarte(joueur, carte);
	}
	
	public void jouerCarteSort(Joueur joueur, Integer idCarte, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		DescripteurCarte carte = this.mappeurIdentifiantsCartes.getValeur(idCarte);
		Ciblable cible = this.mappeurIdentifiantsCiblables.getValeur(idCible);
		if(carte == null || cible == null) {
			throw new IdentifiantInvalideException();
		}
		if(carte instanceof DescripteurSort) {	//TODO: laisser faire le controleur !
			this.modele.jouerCarteSort(joueur, (DescripteurSort) carte, cible);
		}
	}
	
	public void terminerTour(Joueur joueur) throws ViolationReglesException {
		this.modele.terminerTour(joueur);
	}

	public void lancerActionHeros(Joueur joueur) throws ViolationReglesException {
		this.modele.lancerActionHeros(joueur);
	}

	public void lancerActionHeros(Joueur joueur, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		Ciblable cible = this.mappeurIdentifiantsCiblables.getValeur(idCible);
		if(cible == null) {
			throw new IdentifiantInvalideException();
		}
		this.modele.lancerActionHeros(joueur, cible);
	}

	public void attaquer(Joueur joueur, Integer idServiteur, Integer idCible) throws IdentifiantInvalideException, ViolationReglesException {
		Ciblable serviteur = this.mappeurIdentifiantsCiblables.getValeur(idServiteur);
		Ciblable cible = this.mappeurIdentifiantsCiblables.getValeur(idCible);
		if(serviteur == null || cible == null) {
			throw new IdentifiantInvalideException();
		}
		if(serviteur instanceof CarteServiteur) { //TODO: laisser faire le controleur
			this.modele.attaquer(joueur, (CarteServiteur) serviteur, cible);
		}
	}
}
