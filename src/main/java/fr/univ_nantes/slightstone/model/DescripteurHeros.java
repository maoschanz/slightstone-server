package fr.univ_nantes.slightstone.model;

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
public class DescripteurHeros {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@Id
	@Column(name="id_heros")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="points_de_vie")
	private Integer pointsDeVie;
	
	@Column(name="points_armure")
	private Integer pointsArmure;
	
	@Column(name="url_image")
	private String urlImage;
	
	@Column(name="classe_heros")
	@Enumerated(EnumType.STRING)
	private ClasseHeros classe;

	@Column(name="cout_action_speciale")
	private Integer coutActionSpeciale;
	
	@OneToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_carte")
	private DescripteurSort actionSpe;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected DescripteurHeros() {}
	
	public DescripteurHeros (ClasseHeros classe, 
							 String urlImage,
				  			 DescripteurSort actionSpe) {
		this.classe = classe;
		this.urlImage = urlImage;
		this.pointsDeVie = 30;
		this.pointsArmure = 0;
		this.coutActionSpeciale = 2;
		this.actionSpe = actionSpe;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Récupère les points de vie initiaux du héros.
	 * 
	 * @return : points de vie du héros
	 */
	public Integer getPointsVie() {
		return this.pointsDeVie;
	}

	/**
	 * Récupère les points armure initiaux du héros.
	 * 
	 * @return : points armure du héros
	 */
	public Integer getPointsArmure() {
		return this.pointsArmure;
	}
	
	public ClasseHeros getClasse() {
		return this.classe;
	}
	
	public String getUrlImage() {
		return this.urlImage;
	}
	
	/**
	 * Récupère le coût en mana de l'attaque spéciale du héros.
	 * 
	 * @return : coût en mana pour lancer l'attaque spéciale du héros
	 */
	public Integer getCoutActionSpeciale() {
		return this.coutActionSpeciale;
	}
	
	/**
	 * Récupère l'attaque spéciale du héros.
	 * 
	 * @return : carte sort correspondant à l'attaque spéciale du héros
	 */
	public DescripteurSort getActionSpeciale() {
		return this.actionSpe;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	@Override
	public String toString() {
		return String.format("%s avec %d points de vie et %d points d'armure", this.classe, this.pointsDeVie, this.pointsArmure);
	}
}
