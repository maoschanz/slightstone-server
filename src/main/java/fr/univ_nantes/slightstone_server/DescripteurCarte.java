package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class DescripteurCarte {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idType;

	private Integer coutMana;
	private String nom;
	private String description;
	private String imageURL;
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
		return this.idType;
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