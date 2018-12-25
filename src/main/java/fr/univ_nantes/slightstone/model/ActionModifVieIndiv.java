package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="action_modif_vie_indiv")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionModifVieIndiv extends Action {
	@NotNull
	@Column(name="valeur_modif_vie")
	private Integer valeur;
	
	protected ActionModifVieIndiv () {}
	
	public ActionModifVieIndiv (Jeu jeu, TypeCible typeCible, Integer valeur) {
		super(jeu, typeCible);
		this.valeur = valeur;
	}
	
	@Override
	public void executer () {
		// ArrayList<Ciblable> cibles = Jeu.get_cibles();
		// Ciblable cible = cibles.get(0);
		// TODO
		// System.out.println(cible.getPointsDeVie());
		// if (increment < 0) {
		// 	cible.prendreDegats(-1 * this.increment);
		// }
		System.out.println(String.format("Modification de la vie de %d\n", this.valeur));
		// System.out.println(cible.getPointsDeVie());
	}
}