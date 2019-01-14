package fr.univ_nantes.slightstone.model;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestJeu {

	@Test
	public void construireJeu() {
		// With
		Joueur joueur1 = mock(Joueur.class);
		Joueur joueur2 = mock(Joueur.class);
		// When
		Jeu jeu = new Jeu(joueur1, joueur2);
		// Then
		assert (jeu.getJoueurCourant().equals(joueur1));
		assert (jeu.getJoueurAdverse().equals(joueur2));
		assert (jeu.getCibleCourante() == null);
	}

	@Test
	public void initialiserMainJoueurs() {
		// With
		Joueur joueur1 = new Joueur("julien", ClasseHeros.GUERRIER);
		Joueur joueur2 = new Joueur("arthur", ClasseHeros.PALADIN);
		Jeu jeu = new Jeu(joueur1, joueur2);
		// When
		jeu.initialiserMainJoueurs();
		// Then
		assert (joueur1.getMainJoueur().size() == 4);
		assert (joueur2.getMainJoueur().size() == 3);
	}
}
