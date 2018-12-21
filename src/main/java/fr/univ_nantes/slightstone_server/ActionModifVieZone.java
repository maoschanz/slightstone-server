package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id_action")
public class ActionModifVieZone extends Action {
	private Integer increment;
	protected ActionModifVieZone () {}
	
	public ActionModifVieZone (Jeu jeu, TypeCible typeCible, Integer increment) {
		super(jeu);
		this.typeCible = typeCible;
		this.increment = increment; // peut être négatif
	}
	
	@Override
	public void executer () {
		// TODO
	}
}