package fr.univ_nantes.slightstone.model;

public class CarteServiteur implements Ciblable {
	private Integer pointsDeVie;
	private Integer pointsDeDegats;
	private boolean jouable;
	private DescripteurServiteur descripteur;

	protected CarteServiteur () {}

	public CarteServiteur (DescripteurServiteur descripteur) {
		this.descripteur = descripteur;
		this.pointsDeVie = descripteur.getPointsDeVie();
		this.pointsDeDegats = descripteur.getPointsDeDegats();
		this.jouable = false || descripteur.getEffetCharge();
	}

	@Override
	public String toString() {
		return String.format("Carte %s de classe %s avec %d points de vie", this.descripteur.getNom(), this.descripteur.getDescription(), this.descripteur.getPointsDeVie());
	}

	@Override
	public boolean prendreDegats(Integer valeur) {
		if(this.pointsDeVie - valeur >= 0) {
			this.pointsDeVie -= valeur;//TODO points d'armures si il y en a + tuer si 0
		} else {
			this.pointsDeVie = 0;
		}
		return this.pointsDeVie == 0;
	}
	
	@Override
	public void boostDegats(Integer valeur) {
		this.pointsDeVie += valeur;
	}

	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}

	public void setPointsDeVie(Integer pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}

	public Integer getPointsDeDegats() {
		return this.pointsDeDegats;
	}

	public void setPointsDeDegats(Integer pointsDeDegats) {
		this.pointsDeDegats = pointsDeDegats;
	}

	public boolean getJouable() {
		return this.jouable;
	}

	public void setJouable(boolean jouable) {
		this.jouable = jouable;
	}

	public boolean aEffetProvocation() {
		return this.descripteur.getEffetProvocation();
	}

	public boolean aEffetCharge() {
		return this.descripteur.getEffetCharge();
	}

	public boolean aEffetVolDeVie() {
		return this.descripteur.getEffetVolDeVie();
	}

	public boolean aEffetLeader() {
		return this.descripteur.getEffetLeader();
	}
}