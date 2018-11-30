package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;

@Entity
public class ActionInvocationParId extends ActionSansCible {
	private Integer idNouvelleCarte;

	protected ActionInvocationParId () {}
	
	public ActionInvocationParId (Integer idCarte) {
		this.idNouvelleCarte = idCarte;
	}
	
	@Override
	public void performAction () {
		//TODO
	}
}
