package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Cette classe réunit toutes les informations concernant les statistiques d'un serviteur.
 */
@Entity
@Table(name = "serviteurs")
@PrimaryKeyJoinColumn(name = "id_carte")
public class DescripteurServiteur extends DescripteurCarte implements Cloneable {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@Column(name = "points_de_vie")
	private Integer pointsDeVie;

	@Column(name = "points_de_degats")
	private Integer pointsDeDegats;

	@Column(name = "effet_provocation")
	private boolean effetProvocation; // oblige l'adversaire à attaquer ce serviteur plutôt qu'un autre

	@Column(name = "effet_charge")
	private boolean effetCharge; // rend la carte jouable dès qu'elle est invoquée

	@Column(name = "effet_vol_vie")
	private boolean effetVolDeVie; // pour chaque attaque, récupère autant de
									// points de vie que de dégâts infligés

	@Column(name = "effet_leader")
	private boolean effetLeader; // augmente les dégâts de tous les serviteurs sur le plateau de 1 point

	@NotNull
	@Column(name = "type_cible")
	@Enumerated(EnumType.STRING)
	protected TypeCible typeCible; // défini le ou les cibles que peut attaquer le serviteur

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	protected DescripteurServiteur() {}
	
	public DescripteurServiteur(String nom, String description, String imageURL, ClasseHeros classe, 
			Integer coutMana, boolean piochable, Integer pointsDeDegats, Integer pointsDeVie,
			boolean effetProvocation, boolean effetCharge, boolean effetVolDeVie, boolean effetLeader) {
		super(nom, description, imageURL, coutMana, classe, piochable);
		this.pointsDeVie = pointsDeVie;
		this.pointsDeDegats = pointsDeDegats;
		this.effetProvocation = effetProvocation;
		this.effetCharge = effetCharge;
		this.effetVolDeVie = effetVolDeVie;
		this.effetLeader = effetLeader;
		this.typeCible = TypeCible.UN_ADVERSAIRE;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne les points de vie initiaux du serviteur.
	 * 
	 * @return : points de vie du serviteur à l'invocation
	 */
	public Integer getPointsDeVie() {
		return this.pointsDeVie;
	}

	/**
	 * Retourne les points de dégâts initiaux du serviteur.
	 * 
	 * @return : points de dégâts du serviteur à l'invocation
	 */
	public Integer getPointsDeDegats() {
		return this.pointsDeDegats;
	}

	/**
	 * Indique si le serviteur a initialement l'effet "Provocation".
	 * 
	 * @return : true si le serviteur a l'effet Provocation"; false sinon
	 */
	public boolean aEffetProvocation() {
		return this.effetProvocation;
	}

	/**
	 * Indique si le serviteur a initialement l'effet "Charge".
	 * 
	 * @return : true si le serviteur a l'effet "Charge"; false sinon
	 */
	public boolean aEffetCharge() {
		return this.effetCharge;
	}

	/**
	 * Indique si le serviteur a initialement l'effet "Vol de vie".
	 * 
	 * @return : true si le serviteur a l'effet "Vol de vie"; false sinon
	 */
	public boolean aEffetVolDeVie() {
		return this.effetVolDeVie;
	}

	/**
	 * Indique si le serviteur a initialement l'effet "Leader".
	 * 
	 * @return : true si le serviteur a l'effet "Leader"; false sinon
	 */
	public boolean aEffetLeader() {
		return this.effetLeader;
	}

	/**
	 * Retourne le type de cible(s) qu'attaque le serviteur.
	 * 
	 * @return : le type de cible(s) qu'attaque le serviteur
	 */
	public TypeCible getTypeCible() {
		return this.typeCible;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	@Override
	public String toString() {
		return String.format("Carte %s de classe %s avec %d points de vie", this.getNom(), this.getClasse(),
				this.pointsDeVie);
	}
	
	@Override
	public Object clone() {
		Object o = null;
		o = super.clone();
		return o;
	}

	/**
	 * Indique si la carte correspond à une carte sort
	 * 
	 * @return : false
	 */
	@Override
	public boolean estSort() {
		return false;
	}

	/**
	 * Indique si la carte correspond à une carte serviteur
	 * 
	 * @return : true
	 */
	@Override
	public boolean estServiteur() {
		return true;
	}
}
