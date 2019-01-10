package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

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
	 * Récupère la liste des cartes présentes dans la pioche.
	 * 
	 * @return : liste de cartes
	 */
	protected List<DescripteurCarte> getCartes() {
		return this.cartes;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Récupère une carte au hasard dans la pioche.
	 * 
	 * @return : carte tirée aléatoirement
	 */
	public DescripteurCarte piocher() {
		Integer index = (int)(Math.random() * this.cartes.size());
		DescripteurCarte carte = this.cartes.get(index);
		return (DescripteurCarte) carte.clone();
	}
}
