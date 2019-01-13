package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;

@Entity
@Table(name="cartes")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class DescripteurCarte implements Cloneable {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@Id
	@NotNull
	@Column(name="id_carte")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idCarte;
	
	@Column(name="cout_mana")
	private Integer coutMana;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="description")
	private String description;
	
	@Column(name="url_image")
	private String imageURL;
	
	@Column(name="classe")
	@Enumerated(EnumType.STRING)
	private ClasseHeros classe;
	
	@Column(name="piochable")
	private boolean piochable;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	protected DescripteurCarte() {}
	
	public DescripteurCarte (String nom,
	                         String description,
	                         String imageURL,
	                         Integer coutMana,
	                         ClasseHeros classe,
	                         boolean piochable) {
		this.nom = nom;
		this.description = description;
		this.imageURL = imageURL;
		this.coutMana = coutMana;
		this.classe = classe;
		this.piochable = piochable;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	/**
	 * Retourne l'identifiant de la carte dans la bdd
	 * 
	 * @return : l'identifiant de la carte dans la bdd
	 */
	public Integer getIdCarte() {
		return this.idCarte;
	}

	/**
	 * Retourne le nom de la carte
	 * 
	 * @return : le nom de la carte
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourn la description textuelle de la carte
	 * 
	 * @return : une description de la carte
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Retourne l'URL de l'image associée à la carte
	 * 
	 * @return : l'URL de l'image associée à la carte
	 */
	public String getimageURL() {
		return this.imageURL;
	}

	/**
	 * Retourne la classe à laquelle doit appartenir le héros pour pouvoir
	 * utiliser cette carte
	 * 
	 * @return : la classe à laquelle doit appartenir le héros pour pouvoir
	 * utiliser cette carte
	 */
	public ClasseHeros getClasse() {
		return this.classe;
	}

	/**
	 * Retourne le coût en mana nécessaire pour utiliser cette carte
	 * 
	 * @return : coût en mana nécessaire pour utiliser cette carte
	 */
	public Integer getCoutMana() {
		return this.coutMana;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	/**
	 * Indique si la carte correspond à une carte sort
	 * 
	 * @return : true si la carte est un sort; false sinon
	 */
	public abstract boolean estSort();
	
	/**
	 * Indique si la carte correspond à une carte serviteur
	 * 
	 * @return : true si la carte est un serviteur; false sinon
	 */
	public abstract boolean estServiteur();
	
	/**
	 * Vérifie si une quantité donnée de mana est suffisante pour jouer cette carte
	 * 
	 * @param quantiteMana : quantité de mana dont on dispose
	 * @return : true si la quantité de mana est suffisante pour jouer cette carte; false sinon
	 */
	public boolean estManaSuffisante(int quantiteMana) {
		return quantiteMana >= this.coutMana;
	}
	
	@Override
	public String toString() {
		return String.format("Carte %s de classe %s", this.nom, this.classe);
	}
	
	@Override
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			// ne devrait jamais arriver car on implémente l'interface Cloneable
			e.printStackTrace();
		}
		return o;
	}
}