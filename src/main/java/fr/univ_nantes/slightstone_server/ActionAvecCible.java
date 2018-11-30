package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
//@PrimaryKeyJoinColumn(name="idAction")
//@MappedSuperclass
public abstract class ActionAvecCible extends Action {
	private TypeCible typeCible;
	protected ActionAvecCible () {}
	public ActionAvecCible (TypeCible typeCible) {
		super();
		this.typeCible = typeCible;
	}
	public void performAction(Ciblable cible) {}
}
