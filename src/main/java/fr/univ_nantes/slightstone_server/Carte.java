package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;

import java.util.ArrayList;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Carte {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Integer coutMana;
	private String nom;
	private String description;
	private String classe = "commun";
	private ArrayList<Action> actions;
	private boolean piochable;
	
	// needed by JPA
	protected Carte () {}
	
	public Carte (String nom, Integer coutMana, String classe, boolean piochable) {
		this.nom = nom;
		this.coutMana = coutMana;
		this.classe = classe;
		this.piochable = piochable;
	}
	
	public boolean estPiochable() {
		return this.piochable;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getClasse() {
		return this.classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public Integer getCoutMana() {
		return this.coutMana;
	}
	
	public void setCoutMana(Integer coutMana) {
		this.coutMana = coutMana;
	}
	
}
