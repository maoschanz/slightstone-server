package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMainJoueur {
	private MainJoueur hand;

	@BeforeEach
	public void initAll() {
		this.hand = new MainJoueur();
	}
	
	@Test
	public void ajouter() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		Integer length = this.hand.getCartes().size();
		// When
		this.hand.ajouter(descServiteur);
		// Then
		assert (this.hand.getCartes().size() == length + 1);
		assert (this.hand.getCartes().contains(descServiteur));
	}
	
	@Test
	public void retirer() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		Integer length = this.hand.getCartes().size();
		// When
		this.hand.retirer(descServiteur);
		// Then
		assert (this.hand.getCartes().size() == length - 1);
		assert (!this.hand.getCartes().contains(descServiteur));
	}
	
	@Test
	public void contient() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		// When
		boolean b = this.hand.contient(descServiteur);
		// Then
		assert (b = this.hand.getCartes().contains(descServiteur));
	}
}

