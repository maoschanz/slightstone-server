package fr.univ_nantes.slightstone.model;

import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class Heros implements Ciblable {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	private Integer pointsDeVie;
	private Integer pointsArmure;
	private DescripteurHeros descripteur;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	public Heros(DescripteurHeros descripteur) {
		this.pointsDeVie = descripteur.getPointsVie();
		this.pointsArmure = descripteur.getPointsArmure();
		this.descripteur = descripteur;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */

	/**
	 * Retourne les points de vie actuels du héros.
	 * 
	 * @return : points de vie du héros
	 */
	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}
	
	/**
	 * Modifie les points de vie du héros.
	 * 
	 * @param pointsDeVie : nouvelle valeur pour les points de vie du héros
	 */
	protected void setPointsDeVie(Integer pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}

	/**
	 * Retourne les points armure actuels du héros.
	 * 
	 * @return : points armures du héros
	 */
	public Integer getPointsArmure() {
		return this.pointsArmure;
	}
	
	/**
	 * Retourne la description du héros.
	 * 
	 * @return : description du héros
	 */
	public DescripteurHeros getDescripteur() {
		return this.descripteur;
	}
	
	/**
	 * Retourne le type de cible que doit sélectionner le joueur pour lancer
	 * l'attaque spéciale du héros.
	 * 
	 * @return : type de cible que doit sélectionner le joueur
	 */
	public TypeCible getTypeCibleActionSpeciale() {
		return this.descripteur.getActionSpeciale().typeCibleAttendu();
	}
	
	/**
	 * Retourne le coût en mana nécessaire pour lancer l'action spéciale du héros.
	 * 
	 * @return : coût en mana nécessaire pour lancer l'action spéciale du héros
	 */
	public Integer getCoutActionSpeciale() {
		return this.descripteur.getCoutActionSpeciale();
	}
	
	/**
	 * Retourne l'action spéciale du héros
	 * 
	 * @return : la carte sort représentant l'action spéciale du héros
	 */
	public DescripteurSort getActionSpeciale() {
		return this.descripteur.getActionSpeciale();
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Vérifie si le héros est mort
	 * 
	 * @return : true si le héros n'a plus de points de vie; false sinon
	 */
	public boolean estMort() {
		return this.pointsDeVie <= 0;
	}

	/**
	 * Vérifie si le héros à l'effet "Provocation"
	 * 
	 * @return : false
	 */
	@Override
	public boolean aProvocation() {
		return false; // un héros ne peut pas avoir un tel effet
	}
	
	/**
	 * Augmente les points armures du héros.
	 * 
	 * @param valeur : nombre de points armure à ajouter
	 * @throws ValeurNegativeException : levée lorsque valeur < 0
	 */
	void ajouterPointsArmure(Integer valeur) throws ValeurNegativeException {
		if(valeur < 0) {
			throw new ValeurNegativeException("Il est interdit d'ajouter un nombre négatif de points d'armure!");
		}
		this.pointsArmure += valeur;
	}
	
	/**
	 * Lance l'attaque spéciale du héros.
	 */
	void jouerActionSpeciale(Jeu jeu) {
		this.descripteur.getActionSpeciale().lancerActions(jeu);
	}
	
	/**
	 * Diminue les points de vie et/ou les points armures du héros en
	 * fonction des dégâts reçus.
	 * Les points armures sont décrémentés en priorité!
	 * 
	 * Exemple: si un héros a 2 points d'armure 30 points de vie et se voit
	 * infliger 3 points de dégâts, alors, après l'attaque, il lui restera 0 point
	 * d'armure et 29 points de vie.
	 */
	@Override
	public void prendreDegats(Integer degats) {
		if(this.pointsArmure >= degats) {
			this.pointsArmure -= degats;
		} else {
			this.pointsDeVie -= (degats - this.pointsArmure);
			this.pointsArmure = 0;
		}
	}
}
