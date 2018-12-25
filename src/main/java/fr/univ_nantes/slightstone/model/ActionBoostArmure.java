package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="action_boost_armure")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionBoostArmure extends Action {
	@NotNull
	@Column(name="valeur_boost")
	private Integer valeur;
	
	protected ActionBoostArmure () {}
	
	public ActionBoostArmure (Jeu jeu, TypeCible typeCible, Integer valeur) {
		super(jeu, typeCible);
		this.valeur = valeur;
	}
	
	@Override
	public void executer () {
		// toujours sur nous-mêmes TODO
		System.out.println(String.format("Boost armure de %d\n", this.valeur));
	}
}