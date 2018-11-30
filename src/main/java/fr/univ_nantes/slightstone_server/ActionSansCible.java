package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
//@PrimaryKeyJoinColumn(name="idAction*")
//@MappedSuperclass
public abstract class ActionSansCible extends Action {
	protected ActionSansCible () {}
	public void performAction() {}
}
