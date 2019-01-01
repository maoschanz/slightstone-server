package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TestActionMouton {
	
	@Test
	public void executerActionMouton() {
		//With
		DescripteurServiteur descServiteur = new DescripteurServiteur("chevalier", "un chevalier", "chevalier.png", ClasseHeros.COMMUN,
				2, 1, 3, false, false, false, false);
		CarteServiteur cible = new CarteServiteur(descServiteur);
		Jeu jeu = mock(Jeu.class);
		when(jeu.getCibleCourante()).thenReturn(cible);
		ActionMouton actionMouton = new ActionMouton(jeu);		
		//When
		actionMouton.executer();
		//Then
		assert (cible.getPointsDeDegats() == 1);
		assert (cible.getPointsDeVie() == 1);
		assert !(cible.aEffetLeader());
		assert !(cible.aEffetCharge());
		assert !(cible.aEffetProvocation());
		assert !(cible.aEffetVolDeVie());
	}
}
