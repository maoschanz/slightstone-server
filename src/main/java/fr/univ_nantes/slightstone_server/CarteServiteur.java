package fr.univ_nantes.slightstone_server;

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

	public void prendreDegats(Integer valeur) {
		this.pointsDeVie -= valeur;//TODO points d'armures si il y en a + tuer si 0
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
/*
	public boolean getEffetProvocation() {
		return this.effetProvocation;
	}

	public void setEffetProvocation(boolean effetProvocation) {
		this.effetProvocation = effetProvocation;
	}

	public boolean getEffetCharge() {
		return this.effetCharge;
	}

	public void setEffetCharge(boolean effetCharge) {
		this.effetCharge = effetCharge;
	}

	public boolean getEffetVolDeVie() {
		return this.effetVolDeVie;
	}

	public void setEffetVolDeVie(boolean effetVolDeVie) {
		this.effetVolDeVie = effetVolDeVie;
	}

	public boolean getEffetLeader() {
		return this.effetLeader;
	}

	public void setEffetLeader(boolean effetLeader) {
		this.effetLeader = effetLeader;
	}*/
}