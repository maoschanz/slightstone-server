package fr.univ_nantes.slightstone.model.actions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import fr.univ_nantes.slightstone.model.CarteServiteur;
import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.TypeCible;
import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

/**
 * Cette action augmente les points de dégâts d'un des serviteurs du joueur courant
 * Le nombre de points de dégâts ajoutés au serviteur est défini par la variable valeur.
 */
@Entity
@Table(name="action_boost_degats")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionBoostDegats extends Action {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@NotNull
	@Column(name="valeur_boost")
	private Integer valeur; //nombre de points de dégâts ajoutés
		
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected ActionBoostDegats() {}
	
	public ActionBoostDegats(Integer valeur) throws ValeurNegativeException {
		super(TypeCible.UN_SERVITEUR_ALLIE);
		if(valeur < 0) {
			throw new ValeurNegativeException("La valeur du boost doit être positive !");
		}
		this.valeur = valeur;
		
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Augmente les points de dégats du serviteur sélectionné
	 */
	@Override
	public void executer(Jeu jeu) {
		CarteServiteur serviteur = (CarteServiteur) jeu.getCibleCourante();
		serviteur.setPointsDeDegats(serviteur.getPointsDeDegats() + this.valeur);
	}
}
