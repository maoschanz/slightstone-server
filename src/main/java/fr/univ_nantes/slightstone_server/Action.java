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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="actions")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Action {
	@Id
	@Column(name="id_action")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Transient
	protected Jeu jeu;
	
	@NotNull
	@Column(name="type_cible")
	@Enumerated(EnumType.STRING)
	protected TypeCible typeCible;
	
	protected Action() {}

	public Action(Jeu jeu, TypeCible typeCible) {
		this.jeu = jeu;
		this.typeCible = typeCible;
	}

	public abstract void executer();
}