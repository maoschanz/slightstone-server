package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionPiocheRandom extends Action {
	// protected ActionPiocheRandom () {}
	
	public ActionPiocheRandom (Jeu jeu) {
		super(jeu);
	}
	
	@Override
	public void execAction () {
		// TODO
	}
}
