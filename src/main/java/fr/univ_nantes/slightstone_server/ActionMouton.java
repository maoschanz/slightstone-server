package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;

@Entity
public class ActionMouton extends ActionAvecCible {
	
	protected ActionMouton () {}
	
	public ActionMouton (TypeCible cible) {
		super(cible);
	}
	
	@Override
	public void performAction (Ciblable cible) {
		// TODO
	}
}
