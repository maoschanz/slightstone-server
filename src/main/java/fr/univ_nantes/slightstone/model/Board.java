package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Cette classe réunit tous les serviteurs invoqués par un joueur et gère l'invocation
 * de nouveau serviteur.
 * De plus, cette classe a également pour rôle de gérer toute la mécanique autour de l'effet "leader"
 * (les dégâts de tous les serviteurs alliés augmentent de 1 tant que le serviteur est vivant)
 * 
 * Ainsi cette classe s'assure que les 3 points suivants sont respectés :
 * - lorsqu'un serviteur ayant l'effet "leader" est invoqué, les dégâts de tous les serviteurs doivent
 *   augmenter de 1 point
 * - lorsqu'un serviteur ayant l'effet "leader" meurt, les dégâts de tous les serviteurs doivent
 *   diminuer de 1 point
 * - lorsqu'un serviteur est invoqué, qu'il est l'effet "leader" ou non, s'il y a n serviteurs ayant
 *   l'effet "leader" sur le plateau, les dégâts de ce serviteur doivent augmenter de n points
 */
public class Board implements Observer {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private ArrayList<CarteServiteur> serviteurs;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public Board () {
		this.serviteurs = new ArrayList<CarteServiteur>();
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne la liste des serviteurs présents sur le plateau.
	 * 
	 * @return : liste des serviteurs présents sur le plateau
	 */
	public List<CarteServiteur> getCartes() {
		return this.serviteurs;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Vérifie si un serviteur est sur le plateau
	 * 
	 * @param le serviteur que l'on cherche sur le plateau
	 * @return true si le serviteur est présent sur le plateau; false sinon
	 */
	public boolean contient(CarteServiteur serviteur) {
		return this.serviteurs.contains(serviteur);
	}
	
	/**
	 * Vérifie si l'un des serviteurs présents sur le plateau a
	 * l'effet "provocation"
	 * 
	 * @return : true si un serviteur présent sur le plateau a un effet "provocation"; false sinon
	 */
	public boolean contientServiteurAvecProvocation() {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(serviteur.aEffetProvocation()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Augmente les dégats d'un serviteur de 1 point pour chaque serviteur
	 * présent sur le plateau ayant l'effet "Leader".
	 * Si il y a n serviteurs avec l'effet "Leader" sur le plateau, les dégâts
	 * du serviteur invoqué seront augmentés de n points (effet cumulatif).
	 * 
	 * @param serviteurInvoque : nouveau serviteur venant d'être invoqué sur le plateau
	 */
	protected void recupererEffetLeader(CarteServiteur serviteurInvoque) {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(serviteur.aEffetLeader()) {
				serviteurInvoque.boostDegats(1);
			}
		}
	}
	
	/**
	 * Augmente de 1 point les dégâts de chaque serviteur présent sur le plateau
	 */
	protected void nouveauServiteurAvecEffetLeader() {
		for(CarteServiteur serviteur : this.serviteurs) {
			serviteur.boostDegats(1);
		}
	}
	
	/**
	 * Diminue de 1 point les dégâts de chaque serviteur présent sur le plateau
	 */
	protected void perteServiteurAvecEffetLeader() {
		for(CarteServiteur serviteur : this.serviteurs) {
			serviteur.boostDegats(-1);
		}
	}
	
	/**
	 * Crée un nouveau serviteur à partir d'un descripteur et l'ajoute sur le plateau.
	 * Si le serviteur invoqué a l'effet "leader", chaque serviteur présent sur le plateau verra ses dégâts
	 * augmenter de 1 point.
	 * Pour chaque serviteur présent sur le plateau ayant l'effet "leader", le serviteur invoqué verra ses dégâts
	 * augmenter de 1 point.
	 * 
	 * @param descServiteur : description d'un serviteur (nombre de points de vie initiaux, dégats initiaux, ...)
	 */
	void invoquer(DescripteurServiteur descServiteur) {
		CarteServiteur serviteur = new CarteServiteur(descServiteur);
		serviteur.addObserver(this);
		this.recupererEffetLeader(serviteur); // met à jour les dégats du serviteur invoqué
		this.serviteurs.add(serviteur); // ajoute le serviteur sur le plateau
		if(serviteur.aEffetLeader()) { 
			this.nouveauServiteurAvecEffetLeader(); // augmente les dégats de tous les serviteurs de 1
		}
	}

	/**
	 * Retire un serviteur du plateau.
	 * Si le serviteur retiré avait l'effet "leader", on retire 1 point de dégâts
	 * à chaque serviteur présent sur le plateau.
	 * 
	 * @param serviteur : un serviteur présent sur le plateau
	 */
	void retirer(CarteServiteur serviteur) {
		this.serviteurs.remove(serviteur);
		if(serviteur.aEffetLeader()) {
			this.perteServiteurAvecEffetLeader();
		}
	}
	
	/**
	 * Rend tous les serviteurs jouables.
	 */
	void actualiserJouabiliteServiteurs() {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(!serviteur.estJouable()) {
				serviteur.setJouable(true);
			}
		}
	}

	@Override
	public void update(Observable observable, Object arg) {
		CarteServiteur serviteur = (CarteServiteur) observable;
		if(serviteur.getPointsDeVie() <= 0) { 
			this.retirer(serviteur); // le serviteur est mort, on le retire
		}
		if(serviteur.getDescripteur().aEffetLeader() && !serviteur.aEffetLeader()) { 
			this.perteServiteurAvecEffetLeader(); // le serviteur a perdu l'effet "Leader"
		}
	}
}
