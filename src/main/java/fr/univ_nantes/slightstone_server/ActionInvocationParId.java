package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idAction")
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
