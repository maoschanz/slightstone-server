package fr.univ_nantes.slightstone.model;

import java.util.Observable;

import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class CarteServiteur extends Observable implements Ciblable {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private Integer pointsDeVie;
	private Integer pointsDeDegats;
	private boolean jouable; // sert à bloquer la carte temporairement (la rendre injouable)
	private boolean effetProvocation;
	private boolean effetCharge;
	private boolean effetVolDeVie;
	private boolean effetLeader;
	private DescripteurServiteur descripteur;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public CarteServiteur(DescripteurServiteur descripteur) {
		this.descripteur = descripteur;
		this.pointsDeVie = descripteur.getPointsDeVie();
		this.pointsDeDegats = descripteur.getPointsDeDegats();
		this.effetProvocation = descripteur.aEffetProvocation();
		this.effetCharge = descripteur.aEffetCharge();
		this.effetVolDeVie = descripteur.aEffetVolDeVie();
		this.effetLeader = descripteur.aEffetLeader();
		this.jouable = false || this.effetCharge; // l'effet "Charge" permet d'utiliser tout de suite la carte
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne les points de vie actuelle du serviteur.
	 * 
	 * @return : points de vie du serviteur
	 */
	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}

	/**
	 * Modifie les points de vie du serviteur.
	 * 
	 * @param pointsDeVie : nouvelle valeur pour les points de vie
	 */
	void setPointsDeVie(Integer pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}

	/**
	 * Retourne les points de dégâts actuels du serviteur.
	 * 
	 * @return : points de dégâts du serviteur
	 */
	public Integer getPointsDeDegats() {
		return this.pointsDeDegats;
	}

	/**
	 * Modifie les points de dégâts du serviteur.
	 * 
	 * @param pointsDeDegats : nouvelle valeur pour les points de dégâts
	 */
	void setPointsDeDegats(Integer pointsDeDegats) {
		this.pointsDeDegats = pointsDeDegats;
	}

	/**
	 * Indique si la carte est jouable.
	 * 
	 * @return
	 */
	public boolean estJouable() {
		return this.jouable;
	}

	/**
	 * Modifie la valeur de l'attribut jouable du serviteur.
	 * 
	 * @param jouable : nouvelle valeur pour l'attribut jouable
	 */
	void setJouable(boolean jouable) {
		this.jouable = jouable;
	}

	/**
	 * Indique si le serviteur a actuellement l'effet "Provocation".
	 * 
	 * @return
	 */
	public boolean aEffetProvocation() {
		return this.effetProvocation;
	}

	/**
	 * Modifie la valeur de l'attribut effetProvocation du serviteur.
	 * 
	 * @param effetProvocation
	 */
	void setEffetProvocation(boolean effetProvocation) {
		this.effetProvocation = effetProvocation;
	}

	/**
	 * Indique si le serviteur a actuellement l'effet "Charge".
	 * 
	 * @return
	 */
	public boolean aEffetCharge() {
		return this.effetCharge;
	}

	/**
	 * Modifie la valeur de l'attribut effetCharge du serviteur.
	 * 
	 * @param effetCharge
	 */
	void setEffetCharge(boolean effetCharge) {
		this.effetCharge = effetCharge;
	}

	/**
	 * Indique si le serviteur a actuellement l'effet "Vol de vie".
	 * 
	 * @return
	 */
	public boolean aEffetVolDeVie() {
		return this.effetVolDeVie;
	}

	/**
	 * Modifie la valeur de l'attribut effetVolDeVie du serviteur.
	 * 
	 * @param effetVolDeVie
	 */
	void setEffetVolDeVie(boolean effetVolDeVie) {
		this.effetVolDeVie = effetVolDeVie;
	}

	/**
	 * Indique si le serviteur a actuellement l'effet "Leader".
	 * 
	 * @return
	 */
	public boolean aEffetLeader() {
		return this.effetLeader;
	}

	/**
	 * Modifie la valeur de l'attribut effetLeader du serviteur.
	 * Averti le plateau (via le pattern Observable/Observeur) que le statut de l'effet
	 * "Leader" a changé afin de faire les changements qui s'impose.
	 * 
	 * @param effetLeader
	 */
	void setEffetLeader(boolean effetLeader) {
		this.effetLeader = effetLeader;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Retourne le descripteur du serviteur.
	 * 
	 * @return
	 */
	public DescripteurServiteur getDescripteur() {
		return this.descripteur;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	@Override
	public String toString() {
		return String.format("Serviteur avec %d points de vie et %d points de dégâts", this.getPointsDeVie(),
				this.getPointsDeDegats());
	}

	/**
	 * Réduit les points de vie du serviteur en fonction des dégâts reçus.
	 * Averti le plateau (via le pattern Observable/Observeur) que les points de vie
	 * du serviteur ont changé afin de retirer le serviteur du plateau s'il a perdu
	 * tous ses points de vie.
	 */
	@Override
	public void prendreDegats(Integer valeur) {
		this.pointsDeVie -= valeur;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Retourne le type de cible(s) qu'attaque le serviteur.
	 * 
	 * @return : le type de cible(s) qu'attaque le serviteur
	 */
	public TypeCible typeCibleAttendu() {
		return this.descripteur.getTypeCible();
	}

	/**
	 * Vérifie le le serviteur a l'effet "Provocation"
	 */
	@Override
	public boolean aProvocation() {
		return this.aEffetProvocation();
	}
	
	/**
	 * Attaque la cible désignée par le joueur. Pour ce faire, une nouvelle action
	 * attaque est créé avec pour dégâts les dégâts actuels du serviteur et comme
	 * type de cible, le type de cible que peut attaquer le serviteur.
	 * 
	 * @param jeu : le jeu permettant ainsi à l'action de récupérer la/les cible(s) dont elle a besoin
	 */
	void attaquer(Jeu jeu) {
		try {
			ActionAttaque attaque = new ActionAttaque(this.descripteur.getTypeCible(), this.pointsDeDegats);
			attaque.executer(jeu);
			this.jouable = false; // ne peut attaquer qu'une fois par tour
			if (this.effetVolDeVie) {
				this.pointsDeVie += this.pointsDeDegats;
			}
		} catch (ValeurNegativeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Augmente les dégats du serviteur.
	 * 
	 * @param valeur : points de dégâts à ajouter aux dégâts actuels
	 */
	void boostDegats(Integer valeur) {
		this.pointsDeDegats += valeur;
	}
}
