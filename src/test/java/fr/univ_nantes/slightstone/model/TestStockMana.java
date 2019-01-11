package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.exceptions.StockManaException;
import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class TestStockMana {

	@Test
	public void construireStockMana() {
		// When
		StockMana stockMana = new StockMana();
		// Then
		assert (stockMana.getCapacite() == 1);
		assert (stockMana.getQuantite() == 1);
	}

	@Test
	public void construireStockManaAvecCapaciteEtQuantiteDonnees() {
		try {
			StockMana stockMana = new StockMana(6, 4);
			assert (stockMana.getCapacite() == 6);
			assert (stockMana.getQuantite() == 4);
		} catch (ValeurNegativeException | StockManaException e) {
			assert false;
		}
	}

	@Test
	public void construireStockManaAvecCapaciteNegativeImpossible() {
		try {
			@SuppressWarnings("unused")
			StockMana stockMana = new StockMana(-2, 4);
			assert false;
		} catch (ValeurNegativeException | StockManaException e) {
			assert true;
		}
	}

	@Test
	public void construireStockAvecManaQuantiteNegativeImpossible() {
		try {
			@SuppressWarnings("unused")
			StockMana stockMana = new StockMana(6, -2);
			assert false;
		} catch (ValeurNegativeException | StockManaException e) {
			assert true;
		}
	}

	@Test
	public void construireStockManaAvecQuantiteSupCapaciteImpossible() {
		try {
			@SuppressWarnings("unused")
			StockMana stockMana = new StockMana(4, 6);
			assert false;
		} catch (ValeurNegativeException | StockManaException e) {
			assert true;
		}
	}

	@Test
	public void construireStockManaAvecCapaciteSupCapaciteMaxImpossible() {
		try {
			@SuppressWarnings("unused")
			StockMana stockMana = new StockMana(12, 6);
			assert false;
		} catch (ValeurNegativeException | StockManaException e) {
			assert true;
		}
	}

	@Test
	public void depenserManaQuandQuantiteSuffisante() {
		try {
			// With
			StockMana stockMana = new StockMana(10, 6);
			// When
			boolean manaSuffisante = stockMana.depenserMana(4);
			// Then
			assert (manaSuffisante);
			assert (stockMana.getQuantite() == 2);
		} catch (ValeurNegativeException | StockManaException e) {
			assert false;
		}
	}

	@Test
	public void depenserManaQuandQuantitePasSuffisante() {
		try {
			StockMana stockMana = new StockMana(10, 4);
			// When
			boolean manaSuffisante = stockMana.depenserMana(5);
			// Then
			assert !(manaSuffisante);
			assert (stockMana.getQuantite() == 4);
		} catch (ValeurNegativeException | StockManaException e) {
			assert false;
		}
	}

	@Test
	public void augmenterCapaciteManaAvecCapaciteEgalCapaciteMax() {
		try {
			// With
			StockMana stockMana = new StockMana(10, 6);
			// When
			stockMana.augmenterCapacite();
			// Then
			assert (stockMana.getCapacite() == 10);
			assert (stockMana.getQuantite() == 10);
		} catch (ValeurNegativeException | StockManaException e) {
			assert false;
		}
	}

	@Test
	public void augmenterCapaciteManaAvecCapaciteInfCapaciteMax() {
		try {
			// With
			StockMana stockMana = new StockMana(8, 3);
			// When
			stockMana.augmenterCapacite();
			// Then
			assert (stockMana.getCapacite() == 9);
			assert (stockMana.getQuantite() == 9);
		} catch (ValeurNegativeException | StockManaException e) {
			assert false;
		}
	}
}
