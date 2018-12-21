package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;

@Entity
@PrimaryKeyJoinColumn(name="idType")
public class DescripteurSort extends DescripteurCarte {
	private ArrayList<Action> actions;

	// needed by JPA
	protected DescripteurSort () {}

	public DescripteurSort (String nom,
	                        String description,
	                        String imageURL,
	                        Integer coutMana,
	                        TypeHeros classe) {
		super(nom, description, imageURL, coutMana, classe);
		this.actions = new ArrayList<Action>();
	}

	public boolean lancerActions() {
		for(int i=0;i<this.actions.size();i++) {
			this.actions.get(i).executer();
		}
		return false;
	}

	public void addActions (Action action) { //FIXME à enlever car ça viendra du JPA ?
		this.actions.add(action);
	}
}