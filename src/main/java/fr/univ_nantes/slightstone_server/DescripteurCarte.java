package fr.univ_nantes.slightstone_server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="cartes")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class DescripteurCarte {
	@Id
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
	private TypeHeros classe;

	// needed by JPA
	protected DescripteurCarte () {}

	public DescripteurCarte (String nom,
	                         String description,
	                         String imageURL,
	                         Integer coutMana,
	                         TypeHeros classe) {
		this.nom = nom;
		this.description = description;
		this.imageURL = imageURL;
		this.coutMana = coutMana;
		this.classe = classe;
	}

	@Override
	public String toString() {
		return String.format("Carte %s de classe %s", this.nom, this.classe);
	}

	public Integer getIdType() {
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

	public TypeHeros getClasse() {
		return this.classe;
	}

	public Integer getCoutMana() {
		return this.coutMana;
	}
}