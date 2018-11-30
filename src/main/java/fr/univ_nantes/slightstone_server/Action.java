package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@MappedSuperclass
public abstract class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idAction")
	private Integer idAction;
	
	// FIXME ceci est abject
	public void performAction() {}
	public void performAction(Ciblable cible) {}
}
