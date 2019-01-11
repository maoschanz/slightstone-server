package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */

	private boolean tourJoueur1;
	private Joueur joueur1;
	private Joueur joueur2;
	private Ciblable cibleCourante;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	public Jeu(Joueur joueur1, Joueur joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		this.tourJoueur1 = true;
		this.cibleCourante = null;
	}

	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */

	/**
	 * Récupère le joueur à qui c'est le tour de jouer.
	 * 
	 * @return : joueur
	 */
	public Joueur getJoueurCourant() {
		return tourJoueur1 ? joueur1 : joueur2;
	}

	/**
	 * Récupère le joueur à qui ce n'est pas le tour de jouer.
	 * 
	 * @return : joueur
	 */
	public Joueur getJoueurAdverse() {
		return tourJoueur1 ? joueur2 : joueur1;
	}

	/**
	 * Récupère la dernière cible sélectionnée.
	 * 
	 * @return
	 */
	public Ciblable getCibleCourante() {
		return this.cibleCourante;
	}


	/**
	 * Modifie la dernière cible sélectionnée.
	 * 
	 * @param cible : nouvelle cible
	 */
	protected void setCibleCourante(Ciblable cible) {
		this.cibleCourante = cible;
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Initialise la main de chaque joueur. Le joueur à qui c'est le tour commence
	 * avec 3 cartes dans la main. Le joueur à qui ce n'est pas le tour commence
	 * avec 4 cartes dans la main.
	 */
	public void initialiserMainJoueurs() {
		Joueur joueurCourant = this.getJoueurCourant();
		for (int i = 0; i < 3; i++) {
			joueurCourant.piocherCarte();
		}
		Joueur joueurAdverse = this.getJoueurAdverse();
		for (int i = 0; i < 4; i++) {
			joueurAdverse.piocherCarte();
		}
	}

	/**
	 * Ordonne à un serviteur d'attaquer la cible sélectionnée par le joueur.
	 * 
	 * @param attaquant : serviteur qui lance l'attaque
	 * @param cible     : cible attaquée (serviteur adverse ou héros adverse)
	 */
	public void attaquer(CarteServiteur attaquant, Ciblable cible) {
		this.setCibleCourante(cible);
		attaquant.attaquer(this);
	}

	/**
	 * Ordonne au héros de lancer son attaque spéciale.
	 */
	public void lancerActionHeros() {
		this.getJoueurCourant().jouerActionHeros(this);
	}

	/**
	 * Ordonne au héros de lancer son attaque spéciale contre une cible sélectionnée
	 * par le joueur.
	 * 
	 * @param cible : cible attaquée (serviteur adverse ou héros adverse)
	 */
	public void lancerActionHeros(Ciblable cible) {
		this.setCibleCourante(cible);
		this.lancerActionHeros();
	}

	/**
	 * Ordonne au joueur de jouer une carte présente dans sa main.
	 * 
	 * @param carte : carte à jouer
	 */
	public void jouerCarte(DescripteurCarte carte) {
		this.getJoueurCourant().jouerCarte(this, carte);
	}

	/**
	 * Joue une carte avec une ou plusieurs action(s), dont une nécessite une cible.
	 * Cette cible doit être celle définie par le joueur!
	 * 
	 * @param carte : carte avec une ou plusieurs action(s)
	 * @param cible : cible visée par l'action de la carte
	 */
	public void jouerCarte(DescripteurSort sort, Ciblable cible) {
		this.setCibleCourante(cible);
		this.jouerCarte(sort);
	}

	/**
	 * Met fin au tour du joueur courant et initialise le tour du joueur adverse. Au
	 * début de chaque tour :
	 * - le joueur pioche une carte
	 * - la capacité du stock de mana augmente de 1 et est entièrement rechargé
	 * - les serviteurs invoqués au tour précédent (qui n'étaient alors pas
	 * tous jouables) deviennent jouables
	 */
	public void terminerTour() {
		Joueur joueurCourant = this.getJoueurCourant();
		joueurCourant.piocherCarte();
		joueurCourant.augmenterCapaciteMana();
		joueurCourant.actualiserJouabiliteServiteurs();
		this.tourJoueur1 = !this.tourJoueur1;
	}

	public boolean estJoueurCourant(Joueur joueur) {
		return this.getJoueurCourant().equals(joueur);
	}

	public boolean estTermine() {
		return this.getJoueurCourant().getHeros().estMort() || this.getJoueurAdverse().getHeros().estMort();
	}

	/**
	 * Récupère les cibles touchées par l'attaque
	 * 
	 * @return : liste des cibles
	 */
	public List<Ciblable> recupererCibles(TypeCible typeCible) {
		List<Ciblable> cibles = new ArrayList<Ciblable>();
		switch (typeCible) {
		case TOUS_ADVERSAIRES:
			cibles.add(this.getJoueurAdverse().getHeros());
			cibles.addAll(this.getJoueurAdverse().getServiteurs());
			return cibles;
		case TOUS_SERVITEURS:
			cibles.addAll(this.getJoueurAdverse().getServiteurs());
			cibles.addAll(this.getJoueurCourant().getServiteurs());
			return cibles;
		case TOUS_SERVITEURS_ADVERSES:
			cibles.addAll(this.getJoueurAdverse().getServiteurs());
			return cibles;
		case UN_ADVERSAIRE:
		case UN_SERVITEUR_ADVERSE:
		case UN_SERVITEUR_ALLIE:
			cibles.add(this.getCibleCourante());
			return cibles;
		default:
			return cibles;
		}
	}
}
