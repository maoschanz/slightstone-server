package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;

@Entity
public class ActionModifVieZone extends ActionSansCible {
	private TypeCible typeCible;
	private Integer increment;
	
	protected ActionModifVieZone () {}
	
	public ActionModifVieZone (TypeCible typeCible, Integer increment) {
		this.typeCible = typeCible;
		this.increment = increment; // peut être négatif
	}
	
	@Override
	public void performAction () {
		// TODO
	}
}
