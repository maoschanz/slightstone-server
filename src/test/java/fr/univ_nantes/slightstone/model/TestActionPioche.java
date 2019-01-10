package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.actions.ActionPioche;

import static org.mockito.Mockito.*;

public class TestActionPioche {
	
	@Test
	public void executerActionPioche() {
		//With
		Heros heros = mock(Heros.class);
		Deck deck = mock(Deck.class);
		DescripteurSort bouleDeFeu = new DescripteurSort("Boule de feu",
				"Une boule de feu", "bouleFeu.png", ClasseHeros.MAGE, 2);
		when(deck.piocher()).thenReturn(bouleDeFeu);
		Joueur joueur1 = new Joueur("julien", heros, deck, new MainJoueur(), new Board(), new StockMana());
		Joueur joueur2 = new Joueur("nicolas", heros, deck, new MainJoueur(), new Board(), new StockMana());
		Jeu jeu = new Jeu(joueur1, joueur2);
		ActionPioche actionPioche = new ActionPioche();
		//When
		actionPioche.executer(jeu);
		//Then
		assert (jeu.getJoueurCourant().getMainJoueur().size() == 1);
		assert (jeu.getJoueurCourant().getMainJoueur().contains(bouleDeFeu));
	}
}
