package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idAction")
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
