package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;

@Entity
public abstract class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idAction;
}
