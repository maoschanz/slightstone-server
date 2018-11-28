package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idCarte")
public class ActionPiocheRandom extends ActionSansCible {
	protected ActionPiocheRandom () {}
	
//	public ActionPiocheRandom () {
//		
//	}
	
	@Override
	public void performAction () {
		// TODO
	}
}
