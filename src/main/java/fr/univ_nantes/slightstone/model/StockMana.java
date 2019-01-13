package fr.univ_nantes.slightstone.model;

import fr.univ_nantes.slightstone.model.exceptions.StockManaException;
import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class StockMana {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private static final Integer CAPACITE_MAX = 10;
	private Integer capacite; // capacité actuelle du stock (inférieur ou égal à la capacité max)
	private Integer quantite; // quantité de mana actuelle (entre 0 et la capacité actuelle du stock)

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public StockMana () {
		this.capacite = 1;
		this.quantite = 1;
	}
	
	public StockMana(Integer capacite, Integer quantite) throws ValeurNegativeException, StockManaException {
		if(capacite < 0) {
			throw new ValeurNegativeException("La capacité de mana doit être positive !");
		} else if (capacite > StockMana.CAPACITE_MAX) {
			String message = String.format("La capacité du stock ne peut être supérieur à la capacité MAX : %s", StockMana.CAPACITE_MAX);
			throw new StockManaException(message);
		} else if(quantite < 0) {
			throw new ValeurNegativeException("La quantité de mana ne peut pas être négative !");
		} else if(quantite > capacite) {
			throw new StockManaException("La quantité de mana ne peut pas être supérieure à la capacité courante du stock");
		}
		this.capacite = capacite;
		this.quantite = quantite;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */

	/**
	 * Retourne la quantité actuelle du stock de mana.
	 * 
	 * @return : quantité de mana restante
	 */
	public Integer getQuantite() {
		return this.quantite;
	}

	/**
	 * Retourne la capacité actuelle du stock de mana.
	 * 
	 * @return : la capacité actuelle du stock de mana
	 */
	public Integer getCapacite() {
		return this.capacite;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Consomme une quantité donnée de mana.
	 * 
	 * @param mana : la quantité de mana que l'on souhaite dépenser
	 */
	void depenserMana(Integer mana) {
		this.quantite -= mana;
	}
	
	/**
	 * Augmente la capacité du stock de mana et recharge ce dernier.
	 * Si la capacité actuelle est égal à la capacité maximum du stock,
	 * le stock est seulement rechargé.
	 */
	void augmenterCapacite() {
		if(this.capacite < StockMana.CAPACITE_MAX) {
			this.capacite++;
		}
		this.quantite = this.capacite;
	}
}