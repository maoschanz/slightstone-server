package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;

@Entity
public class ActionModifVieIndiv extends ActionAvecCible {
	private Integer increment;

	protected ActionModifVieIndiv () {}
	
	public ActionModifVieIndiv (Integer increment) {
		this.increment = increment;
	}
	
	@Override
	public void performAction (Ciblable cible) {
		// TODO
	}
}
