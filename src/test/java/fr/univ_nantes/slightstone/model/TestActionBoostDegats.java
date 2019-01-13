package fr.univ_nantes.slightstone.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class TestActionBoostDegats {
	private Jeu jeu;
	
	@BeforeEach
	public void initAll() {
		// Initialisation du jeu
		this.jeu = mock(Jeu.class);
		DescripteurServiteur descServiteur = new DescripteurServiteur("chevalier", "un chevalier", "chevalier.png",
				ClasseHeros.COMMUN, 2, true, 1, 3, false, false, false, false);
		CarteServiteur serviteur = new CarteServiteur(descServiteur);
		when(jeu.getCibleCourante()).thenReturn(serviteur);
	}
	
	@Test
	public void construireActionAvecValeurBoostNegativeImpossible() {
		try {
			@SuppressWarnings("unused")
			ActionBoostDegats actionBoostDegats = new ActionBoostDegats(-3);
			assert false;
		} catch (ValeurNegativeException e) {
			assert true;
		}
	}
	
	@Test
	public void executerActionBoostDegats() {
		try {
			//With
			ActionBoostDegats actionBoostDegats = new ActionBoostDegats(3);
			//When
			actionBoostDegats.executer(this.jeu);
			//Then
			CarteServiteur serviteur = (CarteServiteur) this.jeu.getCibleCourante();
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 3);
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}
}
