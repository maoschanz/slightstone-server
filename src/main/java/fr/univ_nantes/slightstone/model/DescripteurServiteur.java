package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="serviteurs")
@PrimaryKeyJoinColumn(name="id_carte")
public class DescripteurServiteur extends DescripteurCarte {
	@Column(name="points_de_vie")
	private Integer pointsDeVie;
	
	@Column(name="points_de_degats")
	private Integer pointsDeDegats;
	
	@Column(name="effet_provocation")
	private boolean effetProvocation;
	
	@Column(name="effet_charge")
	private boolean effetCharge;
	
	@Column(name="effet_vol_vie")
	private boolean effetVolDeVie;
	
	@Column(name="effet_leader")
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