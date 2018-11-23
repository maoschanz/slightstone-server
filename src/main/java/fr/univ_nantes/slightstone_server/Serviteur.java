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
	
}
