package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

import fr.univ_nantes.slightstone.model.jpa.ServiceJpaSlightstone;

public class Deck {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private ArrayList<DescripteurCarte> cartes;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public Deck(ClasseHeros classeHeros) {
		this.cartes = new ArrayList<DescripteurCarte>();
		this.cartes.addAll(ServiceJpaSlightstone.getService().construireDeck(classeHeros));
		System.out.println(this.cartes.size());
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne la liste des cartes présentes dans la pioche.
	 * 
	 * @return : liste des cartes présentes dans la pioche
	 */
	public List<DescripteurCarte> getCartes() {
		return this.cartes;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Retourne une carte au hasard parmi celles de la pioche.
	 * 
	 * @return : une carte tirée aléatoirement
	 */
	DescripteurCarte piocher() {
		Integer index = (int)(Math.random() * this.cartes.size());
		DescripteurCarte carte = this.cartes.get(index);
		return (DescripteurCarte) carte.clone();
	}
}
