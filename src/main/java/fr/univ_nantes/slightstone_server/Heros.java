package fr.univ_nantes.slightstone_server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="heros")
public class Heros {
	@Id
	@Column(name="id_heros")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="points_de_vie")
	private Integer pointsDeVie;
	
	@Column(name="points_armure")
	private Integer pointsArmure;
	
	@Column(name="url_image")
	private String imageURL;
	
	@Column(name="classe_heros")
	@Enumerated(EnumType.STRING)
	private TypeHeros classe;

	@OneToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_carte", nullable=false)
	private DescripteurSort actionSpe;

	protected Heros () {}

	public Heros (TypeHeros classe, 
				  Integer pointsDeVie, 
				  Integer pointsArmure, 
				  DescripteurSort actionSpe) {
		this.classe = classe;
		this.pointsDeVie = pointsDeVie;
		this.pointsArmure = pointsArmure;
		this.actionSpe = actionSpe;
	}

	public Integer getPointsVie() {
		return this.pointsDeVie;
	}

	public Integer getPointsArmure() {
		return this.pointsArmure;
	}

	public void jouerActionSpeciale () {
		this.actionSpe.lancerActions();
	}
}