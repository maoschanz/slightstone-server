package fr.univ_nantes.slightstone.model;

import java.util.List;

import fr.univ_nantes.slightstone.model.jpa.ServiceJpaSlightstone;

public class Joueur {

	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */

	private String pseudo;
	private Heros heros;
	private Deck deck;
	private MainJoueur hand;
	private Board board;
	private StockMana stockMana;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	protected Joueur(String pseudo, Heros heros, Deck deck, MainJoueur hand, Board board, StockMana stockMana) {
		this.pseudo = pseudo;
		this.heros = heros;
		this.deck = deck;
		this.hand = hand;
		this.board = board;
		this.stockMana = stockMana;
	}

	public Joueur(String pseudo, ClasseHeros classeHeros) {
		this.pseudo = pseudo;
		this.heros = new Heros(ServiceJpaSlightstone.getService().getHeros(classeHeros));
		this.hand = new MainJoueur();
		this.deck = new Deck(classeHeros);
		this.board = new Board();
		this.stockMana = new StockMana();
	}

	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */

	/**
	 * Retourne le pseudo du joueur.
	 * 
	 * @return : pseudo du joueur
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Retourne le héros du joueur.
	 * 
	 * @return : héros du joueur
	 */
	public Heros getHeros() {
		return this.heros;
	}

	/**
	 * Retourne la liste des serviteurs invoqués par le joueur (présents sur le
	 * plateau).
	 * 
	 * @return : liste des serviteurs du joueur
	 */
	public List<CarteServiteur> getServiteurs() {
		return this.board.getCartes();
	}

	/**
	 * Retourne la liste des cartes présentes dans la main du joueur.
	 * 
	 * @return : la liste des cartes présentes dans la main du joueur
	 */
	public List<DescripteurCarte> getMainJoueur() {
		return this.hand.getCartes();
	}

	/**
	 * Retourne la liste des cartes présentes dans la pioche du joueur
	 * 
	 * @return : la liste des cartes présentes dans la pioche du joueur
	 */
	protected List<DescripteurCarte> getPioche() {
		return this.deck.getCartes();
	}

	/**
	 * Retourne la capacité de stockage de mana du joueur
	 * 
	 * @return : la capacite en mana du joueur
	 */
	public int getCapaciteMana() {
		return this.stockMana.getCapacite();
	}

	/**
	 * Retourne la quantité de mana actuelle du joueur
	 * 
	 * @return : la quantité de mana du joueur
	 */
	public int getQuantiteMana() {
		return this.stockMana.getQuantite();
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Vérifie si une carte donnée est présente dans la main du joueur
	 * 
	 * @param carte : une carte
	 * @return : true si le joueur a la carte dans sa main; false sinon
	 */
	public boolean possedeCarte(DescripteurCarte carte) {
		return this.hand.contient(carte);
	}

	/**
	 * Vérifie si un serviteur donné est sur le plateau du joueur
	 * 
	 * @param serviteur : un serviteur
	 * @return : true si le serviteur est présent sur le plateau du joueur; false sinon
	 */
	public boolean possedeServiteur(CarteServiteur serviteur) {
		return this.board.contient(serviteur);
	}

	/**
	 * Vérifie si un héros donné est le héros du joueur
	 * 
	 * @param heros : un héros
	 * @return : true si c'est le héros du joueur; false sinon
	 */
	public boolean estHerosJoueur(Heros heros) {
		return this.heros.equals(heros);
	}

	/**
	 * Vérifie si l'un des serviteurs du jouer a l'effet provocation actif
	 * 
	 * @return : true si l'un des serviteurs présents sur le plateau du joueur
	 * a l'effet "Provocation"; false sinon
	 */
	public boolean aServiteurAvecProvocation() {
		return this.board.contientServiteurAvecProvocation();
	}

	/**
	 * Pioche une carte dans le deck et l'ajoute dans la main.
	 */
	void piocherCarte() {
		DescripteurCarte carte = this.deck.piocher();
		this.hand.ajouter(carte);
	}

	/**
	 * Invoque un serviteur sur le plateau.
	 * 
	 * @param descServiteur : la description du serviteur à invoquer
	 */
	void invoquerServiteur(DescripteurServiteur descServiteur) {
		this.board.invoquer(descServiteur);
	}

	/**
	 * Joue une carte présente dans la main du joueur. Si la carte correspond à un
	 * serviteur, on l'invoque sur le plateau. Si la carte correspond à un sort, on
	 * lance les actions associées.
	 * 
	 * @param carte : la carte à jouer
	 */
	void jouerCarte(Jeu jeu, DescripteurCarte carte) {
		int coutCarte = carte.getCoutMana();
		this.stockMana.depenserMana(coutCarte);
		this.hand.retirer(carte);
		if (carte.estSort()) {
			DescripteurSort sort = (DescripteurSort) carte;
			sort.lancerActions(jeu);
		} else {
			DescripteurServiteur descServiteur = (DescripteurServiteur) carte;
			this.invoquerServiteur(descServiteur);
		}
	}

	/**
	 * Lance l'action spéciale du héros.
	 */
	void jouerActionHeros(Jeu jeu) {
		int coutActionHeros = this.heros.getCoutActionSpeciale();
		this.stockMana.depenserMana(coutActionHeros);
		this.heros.jouerActionSpeciale(jeu);
	}

	/**
	 * Augmente la capcité de stockage du stock de mana et le recharge.
	 */
	void augmenterCapaciteMana() {
		this.stockMana.augmenterCapacite();
	}

	/**
	 * Rend tous les serviteurs jouables.
	 */
	void actualiserJouabiliteServiteurs() {
		this.board.actualiserJouabiliteServiteurs();
	}
}
