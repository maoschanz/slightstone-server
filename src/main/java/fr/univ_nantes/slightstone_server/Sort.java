package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Sort extends Carte {
	private ArrayList<Action> actions;

	// needed by JPA
	protected Sort () {}
	
	public Sort (String nom, Integer coutMana, String classe, boolean piochable) {
		super(nom, coutMana, classe, piochable);
		this.actions = new ArrayList<Action>();
	}

	public boolean jouer() {
		for(int i=0;i<this.actions.size();i++) {
			this.actions.get(i).execAction();
		}
		return false;
	}

	public void addActions (Action action) {
		this.actions.add(action);
	}
}