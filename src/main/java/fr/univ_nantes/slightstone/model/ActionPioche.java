package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Cette action pioche une carte dans le deck du joueur courant et la place dans sa main
 */
@Entity
@Table(name="action_pioche")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionPioche extends Action {
	
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected ActionPioche() {}
	
	public ActionPioche(Jeu jeu) {
		super(jeu, TypeCible.AUCUNE); //cette action ne nécessite aucune cible
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Pioche une carte dans le deck du joueur courant et la place dans sa main
	 */
	@Override
	public void executer() {
		Joueur joueurCourant = this.jeu.getJoueurCourant();
		joueurCourant.piocherCarte();
	}
}