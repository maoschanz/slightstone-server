package fr.univ_nantes.slightstone.model;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class TestCarteServiteur {

	@Test
	public void construireCarteServiteurSansEffetCharge() {
		// With
		DescripteurServiteur descServiteur = mock(DescripteurServiteur.class);
		when(descServiteur.getPointsDeVie()).thenReturn(5);
		when(descServiteur.getPointsDeDegats()).thenReturn(2);
		when(descServiteur.aEffetLeader()).thenReturn(false);
		when(descServiteur.aEffetCharge()).thenReturn(false);
		when(descServiteur.aEffetProvocation()).thenReturn(false);
		when(descServiteur.aEffetVolDeVie()).thenReturn(false);

		// When
		CarteServiteur serviteur = new CarteServiteur(descServiteur);

		// Then
		assert (serviteur.getPointsDeDegats() == descServiteur.getPointsDeDegats());
		assert (serviteur.getPointsDeVie() == descServiteur.getPointsDeVie());
		assert (serviteur.aEffetLeader() == descServiteur.aEffetLeader());
		assert (serviteur.aEffetCharge() == descServiteur.aEffetCharge());
		assert (serviteur.aEffetProvocation() == descServiteur.aEffetProvocation());
		assert (serviteur.aEffetVolDeVie() == descServiteur.aEffetVolDeVie());
		assert (serviteur.getTypeCible() == descServiteur.getTypeCible());
		assert (!serviteur.estJouable());
	}

	@Test
	public void construireCarteServiteurAvecEffetCharge() {
		// With
		DescripteurServiteur descServiteur = mock(DescripteurServiteur.class);
		when(descServiteur.getPointsDeVie()).thenReturn(5);
		when(descServiteur.getPointsDeDegats()).thenReturn(2);
		when(descServiteur.aEffetLeader()).thenReturn(false);
		when(descServiteur.aEffetCharge()).thenReturn(true);
		when(descServiteur.aEffetProvocation()).thenReturn(false);
		when(descServiteur.aEffetVolDeVie()).thenReturn(false);

		// When
		CarteServiteur serviteur = new CarteServiteur(descServiteur);

		// Then
		assert (serviteur.getPointsDeDegats() == descServiteur.getPointsDeDegats());
		assert (serviteur.getPointsDeVie() == descServiteur.getPointsDeVie());
		assert (serviteur.aEffetLeader() == descServiteur.aEffetLeader());
		assert (serviteur.aEffetCharge() == descServiteur.aEffetCharge());
		assert (serviteur.aEffetProvocation() == descServiteur.aEffetProvocation());
		assert (serviteur.aEffetVolDeVie() == descServiteur.aEffetVolDeVie());
		assert (serviteur.getTypeCible() == descServiteur.getTypeCible());
		assert (serviteur.estJouable());
	}

	@Test
	public void boostDegatsServiteur() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
		CarteServiteur serviteur = new CarteServiteur(descServiteur);
		// When
		serviteur.boostDegats(2);
		// Then
		assert (serviteur.getPointsDeDegats() == descServiteur.getPointsDeDegats() + 2);
	}

	@Test
	public void prendreDegats() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 5, false, false, false, true);
		CarteServiteur serviteur = new CarteServiteur(descServiteur);
		// When
		serviteur.prendreDegats(2);
		// Then
		assert (serviteur.getPointsDeVie() == descServiteur.getPointsDeVie() - 2);
	}

	@Test
	public void attaquerServiteurAdverseAvecServiteur() {
		// With
		Joueur joueur1 = mock(Joueur.class);
		Joueur joueur2 = mock(Joueur.class);
		Jeu jeu = new Jeu(joueur1, joueur2);
		
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
		CarteServiteur attaquant = new CarteServiteur(descServiteur);
		CarteServiteur cible = new CarteServiteur(descServiteur);

		// When
		jeu.attaquer(attaquant, cible);

		// Then
		assert (jeu.getCibleCourante().equals(cible));
		assert (cible.getPointsDeVie() == descServiteur.getPointsDeVie() - attaquant.getPointsDeDegats());
	}

	@Test
	public void attaquerHerosAdverseSansArmure() {
		// With
		Joueur joueur1 = mock(Joueur.class);
		Joueur joueur2 = mock(Joueur.class);
		Jeu jeu = new Jeu(joueur1, joueur2);

		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsVie()).thenReturn(30);
		when(descHeros.getPointsArmure()).thenReturn(0);
		Heros cible = new Heros(descHeros);

		jeu.setCibleCourante(cible);
		
		DescripteurServiteur descAttaquant = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
		CarteServiteur attaquant = new CarteServiteur(descAttaquant);

		// When
		attaquant.attaquer(jeu);

		// Then
		assert (cible.getPointsDeVie() == descHeros.getPointsVie() - descAttaquant.getPointsDeDegats());
	}

	@Test
	public void attaquerHerosAdverseAvecArmureSupDegatsAttaque() {
		// With
		Joueur joueur1 = mock(Joueur.class);
		Joueur joueur2 = mock(Joueur.class);
		Jeu jeu = new Jeu(joueur1, joueur2);

		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsVie()).thenReturn(30);
		when(descHeros.getPointsArmure()).thenReturn(5);
		Heros cible = new Heros(descHeros);

		jeu.setCibleCourante(cible);
		
		DescripteurServiteur descAttaquant = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
		CarteServiteur attaquant = new CarteServiteur(descAttaquant);

		// When
		attaquant.attaquer(jeu);

		// Then
		assert (cible.getPointsDeVie() == descHeros.getPointsVie());
	}

	@Test
	public void attaquerHerosAdverseAvecArmureInfDegatsAttaque() {
		// With
		Joueur joueur1 = mock(Joueur.class);
		Joueur joueur2 = mock(Joueur.class);
		Jeu jeu = new Jeu(joueur1, joueur2);

		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsVie()).thenReturn(30);
		when(descHeros.getPointsArmure()).thenReturn(2);
		Heros cible = new Heros(descHeros);

		jeu.setCibleCourante(cible);
		
		DescripteurServiteur descAttaquant = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png",
				ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
		CarteServiteur attaquant = new CarteServiteur(descAttaquant);

		// When
		attaquant.attaquer(jeu);

		// Then
		assert (cible.getPointsDeVie() == descHeros.getPointsVie() - (descAttaquant.getPointsDeDegats() - 2));
	}
}
