package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Serviteur extends Carte {
	
	private Integer pointsDeVie;
	private Integer pointsDeDegats;
	private boolean jouable;
	private boolean effetProvocation;
	private boolean effetCharge;
	private boolean effetVolDeVie;
	private boolean effetLeader;
	
	protected Serviteur () {}
	
	public Serviteur (String nom, Integer coutMana, String classe, Integer pointsDeVie, Integer pointsDeDegats, boolean piochable) {
		super(nom, coutMana, classe, piochable);
		this.pointsDeVie = pointsDeVie;
		this.pointsDeDegats = pointsDeDegats;
	}
	
	public Integer getPointDeVie() {
		return this.pointsDeVie;
	}
	
	public void setPointDeVie(Integer pointsDeVie) {
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
	}
}
