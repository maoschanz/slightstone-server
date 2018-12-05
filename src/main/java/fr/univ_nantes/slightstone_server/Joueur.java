package fr.univ_nantes.slightstone_server;

public class Joueur {
	private String pseudo;
	private Heros heros;

	private Deck deck;
	private Hand hand;
	private Board board;

	protected Joueur () {}
	
	public void utiliseCarte(Integer id_carte) {
		//si la carte est dans la main, on appelle .jouer() dessus
		for (int i=0;i<this.hand.size();i++){
			if (this.hand.get(i).getId() == id_carte){
				boolean add_on_board = this.hand.get(i).jouer();
				//TODO retirer de la main
				if (add_on_board){
					//TODO ajouter au plateau
				}
			}
		}



		//si la carte est sur le plateau jsp ce qu'il faut faire avec XXX
	}
}