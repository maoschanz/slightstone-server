package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionBoostArmure extends Action {
	private Integer increment;
	protected ActionBoostArmure () {}
	
	public ActionBoostArmure (Jeu jeu, Integer increment) {
		super(jeu);
		this.increment = increment;
	}
	
	@Override
	public void executer () {
		// toujours sur nous-mêmes TODO
		System.out.println("executer de ActionBoostArmure");
	}
}