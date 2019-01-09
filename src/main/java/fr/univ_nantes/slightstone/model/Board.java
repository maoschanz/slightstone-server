package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
	 * Récupère la liste des cartes serviteurs présent sur le plateau.
	 * 
	 * @return : liste de cartes serviteur
	 */
	public List<CarteServiteur> getCartes() {
		return this.serviteurs;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Augmente les dégats du serviteur de 1 point pour chaque serviteur
	 * présent sur le plateau ayant l'effet "Leader".
	 * Si il y a n serviteurs avec l'effet "Leader" sur la plateau, les dégâts
	 * du serviteur invoqué seront augmentés de n points.
	 * 
	 * Cette méthode est utilisée pour mettre à jour les dégâts du serviteur
	 * invoqué en fonction des effets des serviteurs déjà présent sur le plateau.
	 * 
	 * @param serviteurInvoque
	 */
	protected void recupererEffetLeader(CarteServiteur serviteurInvoque) {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(serviteur.aEffetLeader()) {
				serviteurInvoque.boostDegats(1);
			}
		}
	}
	
	/**
	 * Augmente les dégats de chaque serviteur présent sur le plateau.
	 * 
	 * Cette méthode est utilisée lorsqu'on invoque un serviteur avec
	 * l'effet "Leader".
	 */
	protected void transmettreEffetLeader() {
		for(CarteServiteur serviteur : this.serviteurs) {
			serviteur.boostDegats(1);
		}
	}
	
	/**
	 * Diminue les dégâts de chaque serviteur présent sur le plateau.
	 * 
	 * Cette méthode est utilisée lorsqu'un serviteur avec l'effet "Leader"
	 * meurt ou lorsqu'un serviteur perd l'effet "Leader".
	 */
	protected void perteEffetLeader() {
		for(CarteServiteur serviteur : this.serviteurs) {
			serviteur.boostDegats(-1);
		}
	}
	
	/**
	 * Crée un nouveau serviteur à partir d'une description et l'ajoute sur le plateau.
	 * Si le serviteur invoqué a l'effet "Leader", chaque serviteur présent sur le plateau verra ses dégats
	 * augmentés de 1 point.
	 * Pour chaque serviteur présent sur le plateau ayant l'effet "Leader", le serviteur invoqué verra ses dégats
	 * augmentés de 1 point.
	 * 
	 * @param descServiteur : description d'un serviteur (nombre de points de vie initiaux, dégats initiaux, ...)
	 */
	public void invoquer(DescripteurServiteur descServiteur) {
		CarteServiteur serviteur = new CarteServiteur(descServiteur);
		serviteur.addObserver(this);
		this.recupererEffetLeader(serviteur); // met à jour les dégats du serviteur invoqué
		this.serviteurs.add(serviteur); // ajoute le serviteur sur le plateau
		if(serviteur.aEffetLeader()) { 
			this.transmettreEffetLeader(); // augmente les dégats de tous les serviteurs de 1
		}
	}

	/**
	 * Retire un serviteur de plateau.
	 * Si le serviteur retiré avait l'effet "Leader", on retire 1 point de dégât
	 * à chaque serviteur présent sur le plateau.
	 * 
	 * @param serviteur : serviteur à retirer
	 */
	public void retirer(CarteServiteur serviteur) {
		this.serviteurs.remove(serviteur);
		if(serviteur.aEffetLeader()) {
			this.perteEffetLeader();
		}
	}
	
	/**
	 * Rend tous les serviteurs jouable.
	 */
	public void actualiserJouabiliteServiteurs() {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(!serviteur.estJouable()) {
				serviteur.setJouable(true);
			}
		}
	}
	
	public boolean contient(CarteServiteur serviteur) {
		return this.serviteurs.contains(serviteur);
	}

	@Override
	public void update(Observable observable, Object arg) {
		CarteServiteur serviteur = (CarteServiteur) observable;
		if(serviteur.getPointsDeVie() <= 0) { 
			this.retirer(serviteur); // le serviteur est mort, on le retire
		}
		if(serviteur.getDescripteur().aEffetLeader() && !serviteur.aEffetLeader()) { 
			this.perteEffetLeader(); // le serviteur a perdu l'effet "Leader"
		}
	}

	public boolean contientServiteurAvecProvocation() {
		for(CarteServiteur serviteur : this.serviteurs) {
			if(serviteur.aEffetProvocation()) {
				return true;
			}
		}
		return false;
	}
}