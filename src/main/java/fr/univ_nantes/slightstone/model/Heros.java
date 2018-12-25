package fr.univ_nantes.slightstone.model;

public class Heros {
	private Integer pointsDeVie;
	private Integer pointsArmure;
	private DescripteurHeros descripteur;

	protected Heros () {}

	public Heros (DescripteurHeros descripteur) {
		this.pointsDeVie = descripteur.getPointsVie();
		this.pointsArmure = descripteur.getPointsArmure();
		this.descripteur = descripteur;
	}

	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}

	public Integer getPointsArmure() {
		return this.pointsArmure;
	}

	public void jouerActionSpeciale () {
		this.descripteur.getActionSpeciale().lancerActions();
	}
}