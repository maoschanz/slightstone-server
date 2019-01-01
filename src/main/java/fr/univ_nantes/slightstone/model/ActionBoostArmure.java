package fr.univ_nantes.slightstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Cette action augmente les points armure du héros du joueur courant (à qui c'est le tour)
 * Le nombre de points armure ajoutés au héros est défini par la variable valeur.
 */
@Entity
@Table(name="action_boost_armure")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionBoostArmure extends Action {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@NotNull
	@Column(name="valeur_boost")
	private Integer valeur;
	
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
		
	protected ActionBoostArmure() {}
	
	public ActionBoostArmure (Jeu jeu, Integer valeur) throws ValeurNegativeException {
		super(jeu, TypeCible.AUCUNE); //cette action ne nécessite aucune cible
		if(valeur < 0) {
			throw new ValeurNegativeException("La valeur du boost doit être positive !");
		}
		this.valeur = valeur;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Augmente les points armure du héros du joueur courant
	 */
	@Override
	public void executer () {
		Heros herosAllie = this.jeu.getJoueurCourant().getHeros();
		try {
			herosAllie.ajouterPointsArmure(valeur);
		} catch (ValeurNegativeException e) {
			e.printStackTrace();
		}
	}
}