package fr.univ_nantes.slightstone_server;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ActionAvecCible extends Action {
	private TypeCible typeCible;
	protected ActionAvecCible () {}
	public ActionAvecCible (TypeCible typeCible) {
		super();
		this.typeCible = typeCible;
	}
	public void performAction(Ciblable cible) {}
}
