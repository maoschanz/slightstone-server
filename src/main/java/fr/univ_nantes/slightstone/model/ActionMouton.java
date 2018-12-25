package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="action_mouton")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionMouton extends Action {
	
	protected ActionMouton () {}
	
	public ActionMouton (Jeu jeu, TypeCible typeCible) {
		super(jeu, typeCible);
	}
	
	@Override
	public void executer () {
		// TODO
	}
}