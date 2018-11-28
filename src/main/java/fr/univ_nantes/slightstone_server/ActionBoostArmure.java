package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idAction")
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
