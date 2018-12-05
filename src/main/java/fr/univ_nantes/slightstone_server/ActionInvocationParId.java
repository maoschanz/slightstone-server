package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionInvocationParId extends Action {
	private Integer idNouvelleCarte;
	protected ActionInvocationParId () {}
	
	public ActionInvocationParId (Jeu jeu, Integer idCarte) {
		super(jeu);
		this.idNouvelleCarte = idCarte;
	}
	
	@Override
	public void execAction () {
		//TODO
	}
}