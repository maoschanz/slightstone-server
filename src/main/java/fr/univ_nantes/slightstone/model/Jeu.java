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
	 * Retourne le joueur considéré comme le joueur1
	 * 
	 * @return : le joueur1
	 */
	public Joueur getJoueur1() {
		return this.joueur1;
	}
	
	/**
	 * Retourne le joueur considéré comme le joueur2
	 * 
	 * @return : le joueur2
	 */
	public Joueur getJoueur2() {
		return this.joueur2;
	}
	
	/**
	 * Retourne le joueur à qui c'est le tour de jouer.
	 * 
	 * @return : le joueur dont c'est le tour
	 */
	public Joueur getJoueurCourant() {
		return tourJoueur1 ? joueur1 : joueur2;
	}

	/**
	 * Retourne le joueur à qui ce n'est pas le tour de jouer.
	 * 
	 * @return : le joueur dont ce n'est pas le tour
	 */
	public Joueur getJoueurAdverse() {
		return tourJoueur1 ? joueur2 : joueur1;
	}

	/**
	 * Retourne la dernière cible sélectionnée.
	 * 
	 * @return : la dernière cible sélectionnée
	 */
	public Ciblable getCibleCourante() {
		return this.cibleCourante;
	}


	/**
	 * Modifie la dernière cible sélectionnée.
	 * 
	 * @param cible : nouvelle cible
	 */
	void setCibleCourante(Ciblable cible) {
		this.cibleCourante = cible;
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Vérifie si la partie est terminée (i.e. l'un des deux héros est mort)
	 * 
	 * @return : true si le jeu est terminée; false sinon
	 */
	public boolean estTermine() {
		return this.getJoueurCourant().getHeros().estMort() || this.getJoueurAdverse().getHeros().estMort();
	}
	
	/**
	 * Initialise la main de chaque joueur. Le joueur à qui c'est le tour commence
	 * avec 3 cartes dans la main. Le joueur à qui ce n'est pas le tour commence
	 * avec 4 cartes dans la main.
	 */
	void initialiserMainJoueurs() {
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
	 * Attaque une cible avec un serviteur donné
	 * 
	 * @param attaquant : le serviteur qui lance l'attaque
	 * @param cible : la cible attaquée (serviteur adverse ou héros adverse)
	 */
	void attaquer(CarteServiteur attaquant, Ciblable cible) {
		this.setCibleCourante(cible);
		attaquant.attaquer(this);
	}

	/**
	 * Lance l'attaque spéciale du joueur courant.
	 */
	void lancerActionHeros() {
		this.getJoueurCourant().jouerActionHeros(this);
	}

	/**
	 * Lance l'attaque spéciale du joueur courant contre une cible sélectionnée
	 * par ce dernier.
	 * 
	 * @param cible : la cible attaquée (serviteur adverse ou héros adverse)
	 */
	void lancerActionHeros(Ciblable cible) {
		this.setCibleCourante(cible);
		this.lancerActionHeros();
	}

	/**
	 * Joue une carte présente dans la main du joueur courant.
	 * 
	 * @param carte : carte à jouer
	 */
	void jouerCarte(DescripteurCarte carte) {
		this.getJoueurCourant().jouerCarte(this, carte);
	}

	/**
	 * Joue une carte avec une ou plusieurs action(s), dont une nécessite une cible.
	 * Cette cible est celle définie par le joueur!
	 * 
	 * @param carte : une carte avec une ou plusieurs action(s)
	 * @param cible : la cible visée par l'action de la carte
	 */
	void jouerCarte(DescripteurSort sort, Ciblable cible) {
		this.setCibleCourante(cible);
		this.jouerCarte(sort);
	}

	/**
	 * Met fin au tour du joueur courant et prépare son prochain tour :
	 * - le joueur pioche une carte
	 * - la capacité du stock de mana augmente de 1 et est entièrement rechargé
	 * - les serviteurs invoqués au tour précédent (qui n'étaient alors pas
	 * tous jouables) deviennent jouables
	 * - l'action spéciale du héros est rechargée
	 */
	void terminerTour() {
		Joueur joueurCourant = this.getJoueurCourant();
		joueurCourant.piocherCarte();
		joueurCourant.augmenterCapaciteMana();
		joueurCourant.actualiserJouabiliteServiteurs();
		joueurCourant.getHeros().setActionChargee(true);
		this.tourJoueur1 = !this.tourJoueur1;
	}

	/**
	 * Retourne les cibles correspondant au type de cible visée
	 * 
	 * @return : liste des cibles
	 */
	List<Ciblable> recupererCibles(TypeCible typeCible) {
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
	
	/**
	 * Vérifie si une cible correspond au type de cible attendu
	 * 
	 * @param typeCibleAttendu : un type de cible
	 * @param cible : une cible dont on souhaite savoir si elle correspond
	 * à un type de cible donnée
	 * @return : true si la cible correspond au type de cible attendu ou 
	 * si aucune cible n'est attendue; false sinon
	 */
	boolean estCibleValide(TypeCible typeCibleAttendu, Ciblable cible) {
		switch (typeCibleAttendu) {
		case UN_ADVERSAIRE:
			return this.getJoueurAdverse().getServiteurs().contains(cible)
				|| this.getJoueurAdverse().getHeros().equals(cible);
		case UN_SERVITEUR_ADVERSE:
			return this.getJoueurAdverse().getServiteurs().contains(cible);
		case UN_SERVITEUR_ALLIE:
			return this.getJoueurCourant().getServiteurs().contains(cible);
		case AUCUNE:
		case TOUS_ADVERSAIRES:
		case TOUS_SERVITEURS:
		case TOUS_SERVITEURS_ADVERSES:
			return true;
		default:
			return false;
		}
	}
}
