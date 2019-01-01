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
public abstract class DescripteurCarte {
	
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

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	protected DescripteurCarte() {}
	
	public DescripteurCarte (String nom,
	                         String description,
	                         String imageURL,
	                         Integer coutMana,
	                         ClasseHeros classe) {
		this.nom = nom;
		this.description = description;
		this.imageURL = imageURL;
		this.coutMana = coutMana;
		this.classe = classe;
	}
	
	/* ******************************** */
	/* ********** Accesseurs ********** */
	/* ******************************** */
	
	public Integer getIdCarte() {
		return this.idCarte;
	}

	public String getNom() {
		return this.nom;
	}

	public String getDescription() {
		return this.description;
	}

	public String getimageURL() {
		return this.imageURL;
	}

	public ClasseHeros getClasse() {
		return this.classe;
	}

	public Integer getCoutMana() {
		return this.coutMana;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	@Override
	public String toString() {
		return String.format("Carte %s de classe %s", this.nom, this.classe);
	}
}