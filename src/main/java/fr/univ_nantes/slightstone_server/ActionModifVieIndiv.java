package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionModifVieIndiv extends Action {
	private Integer increment;
	protected ActionModifVieIndiv () {}
	
	public ActionModifVieIndiv (Jeu jeu, Integer increment) {
		super(jeu);
		this.increment = increment;
	}
	
	@Override
	public void execAction () {
		// ArrayList<Ciblable> cibles = Jeu.get_cibles();
		// Ciblable cible = cibles.get(0);
		// TODO
		// System.out.println(cible.getPointsDeVie());
		// if (increment < 0) {
		// 	cible.prendreDegats(-1 * this.increment);
		// }
		System.out.println("execAction de ActionModifVieIndiv");
		// System.out.println(cible.getPointsDeVie());
	}
}