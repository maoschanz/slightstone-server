package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="action_invocation")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionInvocation extends Action {
	@OneToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_serviteur", nullable=false)
	private DescripteurServiteur descServiteur;
	
	protected ActionInvocation () {}
	
	public ActionInvocation (Jeu jeu, TypeCible typeCible, DescripteurServiteur descServiteur) {
		super(jeu, typeCible);
		this.descServiteur = descServiteur;
	}
	
	@Override
	public void executer () {
		System.out.println(String.format("Invocation du serviteur %s\n", this.descServiteur.getNom()));
	}
}