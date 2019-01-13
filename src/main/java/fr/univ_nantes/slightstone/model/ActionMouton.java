package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Cette action transforme un serviteur sélectionné par le joueur courant en un
 * serviteur dont les statistiques sont 1/1, sans effet spécial.
 */
@Entity
@Table(name="action_mouton")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionMouton extends Action {
	
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
		
	public ActionMouton() {
		super(TypeCible.UN_SERVITEUR_ADVERSE); //la cible doit être un serviteur adverse
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Récupère le serviteur sélectionné par le joueur et le transforme en un
	 * serviteur 1/1 sans effet spécial
	 */
	@Override
	void executer(Jeu jeu) {
		CarteServiteur serviteur = (CarteServiteur) jeu.getCibleCourante();
		serviteur.setPointsDeDegats(1);
		serviteur.setPointsDeVie(1);
		serviteur.setEffetCharge(false);
		serviteur.setEffetLeader(false);
		serviteur.setEffetProvocation(false);
		serviteur.setEffetVolDeVie(false);
	}
}
