package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

public class MainJoueur {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private ArrayList<DescripteurCarte> cartes;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public MainJoueur() {
		this.cartes = new ArrayList<DescripteurCarte>();
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne les cartes présentes dans la main du joueur.
	 * 
	 * @return : liste de cartes
	 */
	public List<DescripteurCarte> getCartes() {
		return this.cartes;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Vérifie si une carte données est présente dans la main du joueur
	 * @param carte : une carte que l'on cherche
	 * @return : true si la carte est présente dans la main du joueur; false sinon
	 */
	public boolean contient(DescripteurCarte carte) {
		return this.cartes.contains(carte);
	}
	
	/**
	 * Ajoute une carte dans la main du joueur.
	 * 
	 * @param carte : carte à ajouter
	 */
	void ajouter(DescripteurCarte carte) {
		this.cartes.add(carte);
	}

	/**
	 * Retire une carte de la main du joueur.
	 * 
	 * @param carte : carte à retirer
	 */
	void retirer(DescripteurCarte carte) {
		this.cartes.remove(carte);
	}
}
