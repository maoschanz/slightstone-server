package fr.univ_nantes.slightstone.model.actions;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.Joueur;
import fr.univ_nantes.slightstone.model.TypeCible;

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
		
	public ActionPioche() {
		super(TypeCible.AUCUNE); //cette action ne nécessite aucune cible
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Pioche une carte dans le deck du joueur courant et la place dans sa main
	 */
	@Override
	public void executer(Jeu jeu) {
		Joueur joueurCourant = jeu.getJoueurCourant();
		joueurCourant.piocherCarte();
	}
}