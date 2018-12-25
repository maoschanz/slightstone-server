package fr.univ_nantes.slightstone.model;

public class Joueur {
	private String pseudo;
	private Heros heros;
	private Deck deck;
	private Hand hand;
	private Board board;

	public Joueur(String pseudo, Heros heros, Deck deck, Hand hand, Board board) {
		this.pseudo = pseudo;
		this.heros = heros;
		this.deck = deck;
		this.hand = hand;
		this.board = board;
	}
	
	public Joueur (String pseudo, Heros heros) {
		this.pseudo = pseudo;
		this.heros = heros;
		this.hand = new Hand();
		this.deck = new Deck();
		this.board = new Board();
	}

	public void initDeck(Deck deck) {
		this.deck = deck;
	}
	
	public void jouerCarte(DescripteurCarte carte) {
		hand.retirer(carte);
		if(carte instanceof DescripteurSort) {
			DescripteurSort sort = (DescripteurSort) carte;
			sort.lancerActions();
		} else {
			CarteServiteur serviteur = new CarteServiteur((DescripteurServiteur) carte);
			board.poser(serviteur);
		}
	}
}