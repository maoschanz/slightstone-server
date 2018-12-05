package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionMouton extends Action {
	protected ActionMouton () {}
	
	public ActionMouton (Jeu jeu, TypeCible cible) {
		super(jeu);
	}
	
	@Override
	public void execAction () {
		// TODO
	}
}