package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.Test;

public class TestBoard {

	@Test
	public void poserCarteServiteur() {
		//With
		Board board = new Board();
		//When
		DescripteurServiteur descServiteur1 = new DescripteurServiteur("chevalier", "un chevalier", "chevalier.png", 2,
				TypeHeros.PALADIN, 3, 1, false, false, false, false);
		CarteServiteur serviteur1 = new CarteServiteur(descServiteur1);
		board.poser(serviteur1);
		//Then
		assert board.getServiteurs().contains(serviteur1);
	}
	
	@Test
	public void poserCarteServiteurSansEffetLeader() {
		// With
		Board board = new Board();
		DescripteurServiteur descServiteur1 = new DescripteurServiteur("chevalier", "un chevalier", "chevalier.png", 2,
				TypeHeros.PALADIN, 3, 1, false, false, false, false);
		CarteServiteur serviteur1 = new CarteServiteur(descServiteur1);
		board.poser(serviteur1);
		// When
		DescripteurServiteur descServiteur2 = new DescripteurServiteur("dragon", "un dragon", "dragon.png", 2,
				TypeHeros.PALADIN, 5, 3, false, false, false, false);
		CarteServiteur serviteur2 = new CarteServiteur(descServiteur2);
		board.poser(serviteur2);
		// Then
		assert (serviteur1.getPointsDeDegats() == descServiteur1.getPointsDeDegats());
		assert (serviteur2.getPointsDeDegats() == descServiteur2.getPointsDeDegats());
	}

	@Test
	public void poserCarteServiteurAvecEffetLeader() {
		// With
		Board board = new Board();
		DescripteurServiteur descServiteur1 = new DescripteurServiteur("chevalier", "un chevalier", "chevalier.png", 2,
				TypeHeros.PALADIN, 3, 1, false, false, false, false);
		CarteServiteur serviteur1 = new CarteServiteur(descServiteur1);
		board.poser(serviteur1);
		// When
		DescripteurServiteur descServiteur2 = new DescripteurServiteur("dragon", "un dragon", "dragon.png", 2,
				TypeHeros.PALADIN, 5, 3, false, false, false, true);
		CarteServiteur serviteur2 = new CarteServiteur(descServiteur2);
		board.poser(serviteur2);
		// Then
		assert (serviteur1.getPointsDeDegats() == descServiteur1.getPointsDeDegats() + 1);
		assert (serviteur2.getPointsDeDegats() == descServiteur2.getPointsDeDegats() + 1);
	}
}
