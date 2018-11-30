package fr.univ_nantes.slightstone_server;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ActionSansCible extends Action {
	protected ActionSansCible () {}
	public void performAction() {}
}
