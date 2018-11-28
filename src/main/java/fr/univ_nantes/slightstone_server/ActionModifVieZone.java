package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idCarte")
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
