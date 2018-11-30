package fr.univ_nantes.slightstone_server;

import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@MappedSuperclass
public abstract class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idAction;
}
