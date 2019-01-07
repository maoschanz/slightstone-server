package fr.univ_nantes.slightstone.model;

public class Heros implements Ciblable{
	
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
	 * Récupère les points de vie actuelle du héros.
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
	 * Récupère les points armure actuelle du héros.
	 * 
	 * @return : points armure du héros
	 */
	public Integer getPointsArmure() {
		return this.pointsArmure;
	}
	
	/**
	 * Récupère la description du héros.
	 * 
	 * @return : description du héros
	 */
	protected DescripteurHeros getDescripteur() {
		return this.descripteur;
	}
	
	/**
	 * Récupère le type de cible que doit sélectionner le joueur pour lancer
	 * l'attaque spéciale du héros.
	 * 
	 * @return : type de cible que doit sélectionner le joueur
	 */
	public TypeCible getTypeCibleActionSpeciale() {
		return this.descripteur.getActionSpeciale().cibleASelectionner();
	}
	
	/**
	 * Récupère le coût en mana de l'attaque spéciale.
	 * 
	 * @return : coût en mana de l'attaque spéciale
	 */
	public Integer getCoutActionSpeciale() {
		return this.descripteur.getCoutActionSpeciale();
	}
	
	public DescripteurSort getActionSpeciale() {
		return this.descripteur.getActionSpeciale();
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Augmente les points armure du héros.
	 * 
	 * @param valeur : nombre de points armure à ajouter
	 * @throws ValeurNegativeException : levée lorsque valeur < 0
	 */
	public void ajouterPointsArmure(Integer valeur) throws ValeurNegativeException {
		if(valeur < 0) {
			throw new ValeurNegativeException("Il est interdit d'ajouter un nombre négatif de points d'armure!");
		}
		this.pointsArmure += valeur;
	}
	
	/**
	 * Lance l'attaque spéciale du héros.
	 */
	public void jouerActionSpeciale() {
		this.descripteur.getActionSpeciale().lancerActions();
	}
	
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