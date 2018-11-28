package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Id;

@Entity
@PrimaryKeyJoinColumn(name="idAction")
public abstract class ActionAvecCible extends Action {
	private TypeCible typeCible;
	protected ActionAvecCible () {}
	public ActionAvecCible (TypeCible typeCible) {
		super();
		this.typeCible = typeCible;
	}
	public void performAction(Ciblable cible) {}
}
