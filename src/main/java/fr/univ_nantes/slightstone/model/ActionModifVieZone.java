package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="action_modif_vie_zone")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionModifVieZone extends Action {
	@NotNull
	@Column(name="valeur_modif_vie")
	private Integer valeur;
	
	protected ActionModifVieZone () {}
	
	public ActionModifVieZone (Jeu jeu, TypeCible typeCible, Integer valeur) {
		super(jeu, typeCible);
		this.valeur = valeur; // peut être négatif
	}
	
	@Override
	public void executer () {
		// TODO
	}
}