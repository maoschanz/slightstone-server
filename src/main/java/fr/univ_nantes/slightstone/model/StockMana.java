package fr.univ_nantes.slightstone.model;

public class StockMana {
	private final Integer CAPACITE_MAX;
	private Integer capacite;
	private Integer quantite;

	public StockMana (Integer max) {
		this.CAPACITE_MAX = max;
		this.capacite = max;
		this.quantite = max;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public void setCapacite(Integer capacite) {
		this.capacite = capacite;
	}

	public Integer getQuantite() {
		return this.quantite;
	}

	public Integer getCapacite() {
		return this.capacite;
	}

	public Integer getMax() {
		return this.CAPACITE_MAX;
	}
}