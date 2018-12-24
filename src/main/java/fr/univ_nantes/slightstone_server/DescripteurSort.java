package fr.univ_nantes.slightstone_server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="sorts")
@PrimaryKeyJoinColumn(name="id_carte")
public class DescripteurSort extends DescripteurCarte {
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="id_action", nullable=false)
	private List<Action> actions;

	protected DescripteurSort () {}

	public DescripteurSort (String nom,
	                        String description,
	                        String imageURL,
	                        Integer coutMana,
	                        TypeHeros classe) {
		super(nom, description, imageURL, coutMana, classe);
		this.actions = new ArrayList<Action>();
	}

	public void lancerActions() {
		System.out.println("actions carte sort !");
		actions.stream().forEach( action -> { action.executer(); } );
	}

	public void ajouterAction (Action action) {
		this.actions.add(action);
	}
}