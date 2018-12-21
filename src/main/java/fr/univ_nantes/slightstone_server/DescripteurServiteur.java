package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="idType")
public class DescripteurServiteur extends DescripteurCarte {

	private Integer pointsDeVie;
	private Integer pointsDeDegats;
	private boolean effetProvocation;
	private boolean effetCharge;
	private boolean effetVolDeVie;
	private boolean effetLeader;

	protected DescripteurServiteur () {}

	public DescripteurServiteur (String nom,
                                String description,
                                String imageURL,
                                Integer coutMana,
                                TypeHeros classe,
                                Integer pointsDeVie,
                                Integer pointsDeDegats,
                                boolean effetProvocation,
                                boolean effetCharge,
                                boolean effetVolDeVie,
                                boolean effetLeader) {
		super(nom, description, imageURL, coutMana, classe);
		this.pointsDeVie = pointsDeVie;
		this.pointsDeDegats = pointsDeDegats;
		this.effetProvocation = effetProvocation;
		this.effetCharge = effetCharge;
		this.effetVolDeVie = effetVolDeVie;
		this.effetLeader = effetLeader;
	}

	@Override
	public String toString() {
		return String.format("Carte %s de classe %s avec %d points de vie", this.getNom(), this.getClasse(), this.pointsDeVie);
	}

	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}

	public Integer getPointsDeDegats() {
		return this.pointsDeDegats;
	}

	public boolean getEffetProvocation() {
		return this.effetProvocation;
	}

	public boolean getEffetCharge() {
		return this.effetCharge;
	}

	public boolean getEffetVolDeVie() {
		return this.effetVolDeVie;
	}

	public boolean getEffetLeader() {
		return this.effetLeader;
	}
}