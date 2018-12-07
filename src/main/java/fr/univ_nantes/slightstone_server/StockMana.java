package fr.univ_nantes.slightstone_server;

public class StockMana {
	private final Integer CAPACITE_MAX;
	private Integer capacite;
	private Integer quantite;

	public StockMana (Integer max) {
		this.CAPACITE_MAX = max;
		this.capacite = max;
		this.quantite = max;
	}

	public void setQuantite(Integer q) {
		this.quantite = q;
	}

	public void setCapacite(Integer c) {
		this.capacite = c;
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