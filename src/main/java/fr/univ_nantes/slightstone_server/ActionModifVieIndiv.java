package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idCarte")
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
