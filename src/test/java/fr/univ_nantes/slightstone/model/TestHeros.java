package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TestHeros {
	private Heros heros;

	@BeforeEach
	public void initAll() {
		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsArmure()).thenReturn(0);
		when(descHeros.getPointsVie()).thenReturn(30);
		this.heros = new Heros(descHeros);
	}

	@Test
	public void construireHeros() {
		// With
		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsArmure()).thenReturn(0);
		when(descHeros.getPointsVie()).thenReturn(30);
		// When
		Heros heros = new Heros(descHeros);
		// Then
		assert (heros.getPointsDeVie() == descHeros.getPointsVie());
		assert (heros.getPointsArmure() == descHeros.getPointsArmure());
	}
	
	@Test
	public void ajouterNombrePositifPointArmure() {
		try {
			// When
			this.heros.ajouterPointsArmure(10);
			// Then
			assert (this.heros.getPointsArmure() == 10);
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}
	
	@Test
	public void ajouterNombreNegatifPointArmureImpossible() {
		try {
			this.heros.ajouterPointsArmure(-10);
			assert false;
		} catch (ValeurNegativeException e) {
			assert true;
		}
	}

	@Test
	public void attaquerHerosAvecArmureSupDegats() {
		try {
			// With
			this.heros.ajouterPointsArmure(10);
			// When
			this.heros.prendreDegats(5);
			// Then
			assert (this.heros.getPointsDeVie() == this.heros.getDescripteur().getPointsVie());
			assert (this.heros.getPointsArmure() == 5);
		} catch (ValeurNegativeException e) {
			assert false;
		}
		
	}

	@Test
	public void attaquerHerosAvecArmureInfDegats() {	
		try {
			// With
			this.heros.ajouterPointsArmure(3);
			// When
			this.heros.prendreDegats(5);
			// Then
			assert (this.heros.getPointsDeVie() == this.heros.getDescripteur().getPointsVie() - 2);
			assert (this.heros.getPointsArmure() == 0);
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}
}
