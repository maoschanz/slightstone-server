package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;

@Entity
public class ActionBoostArmure extends ActionSansCible {
	private Integer increment;
	protected ActionBoostArmure () {}
	
	public ActionBoostArmure (Integer increment) {
		this.increment = increment;
	}
	
	@Override
	public void performAction () {
		// toujours sur nous-mêmes TODO
	}
}
