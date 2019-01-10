package fr.univ_nantes.slightstone.model;

import java.util.List;

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
	 * Récupère le pseudo du joueur.
	 * 
	 * @return : pseudo du joueur
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * Récupère le héros du joueur.
	 * 
	 * @return : héros du joueur
	 */
	public Heros getHeros() {
		return this.heros;
	}

	/**
	 * Récupère la liste des serviteurs invoqués par le joueur (présent sur le
	 * plateau).
	 * 
	 * @return : liste des serviteurs du joueur
	 */
	public List<CarteServiteur> getServiteurs() {
		return this.board.getCartes();
	}

	/**
	 * Récupère la liste des cartes présentes dans la main du joueur.
	 * 
	 * @return : main du joueur
	 */
	public List<DescripteurCarte> getMainJoueur() {
		return this.hand.getCartes();
	}

	protected List<DescripteurCarte> getPioche() {
		return this.deck.getCartes();
	}

	public int getCapaciteMana() {
		return this.stockMana.getCapacite();
	}

	public int getQuantiteMana() {
		return this.stockMana.getQuantite();
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Pioche une carte dans le deck et l'ajoute dans la main.
	 */
	public void piocherCarte() {
		DescripteurCarte carte = this.deck.piocher();
		this.hand.ajouter(carte);
	}

	/**
	 * Invoque un serviteur sur le plateau.
	 * 
	 * @param descServiteur : description du serviteur à invoquer
	 */
	public void invoquerServiteur(DescripteurServiteur descServiteur) {
		this.board.invoquer(descServiteur);
	}

	/**
	 * Joue une carte présente dans la main du joueur. Si la carte correspond à un
	 * serviteur, on l'invoque sur le plateau. Si la carte correspond à un sort, on
	 * lance les actions associées.
	 * 
	 * @param carte : carte à jouer
	 */
	public void jouerCarte(Jeu jeu, DescripteurCarte carte) {
		int coutCarte = carte.getCoutMana();
		this.stockMana.depenserMana(coutCarte);
		this.hand.retirer(carte);
		if (carte instanceof DescripteurSort) {
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
	public void jouerActionHeros(Jeu jeu) {
		int coutActionHeros = this.heros.getCoutActionSpeciale();
		this.stockMana.depenserMana(coutActionHeros);
		this.heros.jouerActionSpeciale(jeu);
	}

	/**
	 * Augmente la capcité de stockage du stock de mana et le recharge.
	 */
	public void augmenterCapaciteMana() {
		this.stockMana.augmenterCapacite();
	}

	/**
	 * Rend tous les serviteurs jouable.
	 * 
	 * Cette méthode est utilisée au début de chaque tour pour rendre jouable les
	 * serviteurs invoqués au tour précédent.
	 */
	public void actualiserJouabiliteServiteurs() {
		this.board.actualiserJouabiliteServiteurs();
	}

	public boolean possedeCarte(DescripteurCarte carte) {
		return this.hand.contient(carte);
	}

	public boolean possedeServiteur(CarteServiteur serviteur) {
		return this.board.contient(serviteur);
	}

	public boolean estHerosJoueur(Heros heros) {
		return this.heros.equals(heros);
	}

	public boolean aServiteurAvecProvocation() {
		return this.board.contientServiteurAvecProvocation();
	}
}