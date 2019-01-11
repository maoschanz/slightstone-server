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
	 * Récupère les cartes présentes dans la main du joueur.
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
	 * Ajoute une carte dans la main du joueur.
	 * 
	 * @param carte : carte à ajouter
	 */
	public void ajouter(DescripteurCarte carte) {
		this.cartes.add(carte);
	}

	/**
	 * Retire une carte de la main du joueur.
	 * 
	 * @param carte : carte à retirer
	 */
	public void retirer(DescripteurCarte carte) {
		this.cartes.remove(carte);
	}

	/**
	 * Vérifie si une carte est dans la main du joueur.
	 * 
	 * @param carte : carte à vérifier
	 * @return boolean : si la carte est la main
	 */
	public boolean contient(DescripteurCarte carte) {
		return this.cartes.contains(carte);
	}
}
