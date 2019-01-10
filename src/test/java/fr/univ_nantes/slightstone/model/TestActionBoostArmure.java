package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.actions.ActionBoostArmure;
import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

import static org.mockito.Mockito.*;

public class TestActionBoostArmure {
	private Jeu jeu;
	
	@BeforeEach
	public void initAll() {
		// Initialisation du jeu
		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsVie()).thenReturn(30);
		when(descHeros.getPointsArmure()).thenReturn(0);
		Heros herosJ1 = new Heros(descHeros);
		Deck deckJ1 = mock(Deck.class);
		Joueur joueur1 = new Joueur("julien", herosJ1, deckJ1, new MainJoueur(), new Board(), new StockMana());
		Heros herosJ2 = new Heros(descHeros);
		Deck deckJ2 = mock(Deck.class);
		Joueur joueur2 = new Joueur("nicolas", herosJ2, deckJ2, new MainJoueur(), new Board(), new StockMana());
		this.jeu = new Jeu(joueur1, joueur2);
	}
	
	@Test
	public void construireActionAvecValeurBoostNegativeImpossible() {
		try {
			@SuppressWarnings("unused")
			ActionBoostArmure actionBoostArmure = new ActionBoostArmure(-2);
			assert false;
		} catch (ValeurNegativeException e) {
			assert true;
		}
	}
	
	@Test
	public void executerActionBoostArmure() {
		try {
			// With
			ActionBoostArmure actionBoostArmure = new ActionBoostArmure(2);
			// When
			actionBoostArmure.executer(this.jeu);
			// Then
			Heros herosAllie = this.jeu.getJoueurCourant().getHeros();
			assert (herosAllie.getPointsArmure() == 2);
		} catch (ValeurNegativeException e) {
			assert false;
		}
		
	}
}
