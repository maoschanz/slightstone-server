package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.util.ArrayList;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	protected TypeCible typeCible;
	protected Action() {}

	@Transient
	protected Jeu jeu;

	public Action(Jeu jeu) {
		this.jeu = jeu;
	}

	public abstract void executer();
}