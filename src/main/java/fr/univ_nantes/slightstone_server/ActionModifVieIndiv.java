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
		System.out.println(cible.getPointsDeVie());
		if (increment < 0) {
			cible.prendreDegats(-1 * this.increment);
		}
		System.out.println("performAction de ActionModifVieIndiv");
		System.out.println(cible.getPointsDeVie());
	}
}
