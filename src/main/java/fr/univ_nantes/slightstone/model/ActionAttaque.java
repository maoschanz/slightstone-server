package fr.univ_nantes.slightstone.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

/**
 * Cette action inflige des dégâts à une ou plusieurs cibles lorsqu'elle est exécutée.
 * Les cibles touchées sont définies par le type TypeCible défini dans la superclasse Action.
 * La valeur des dégats infligés à chaque cible est définie par l'attribut valeur.
 */
@Entity
@Table(name="action_attaque")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionAttaque extends Action {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@NotNull
	@Column(name="degats")
	private Integer valeur; //dégats infligés à chaque cible
		
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected ActionAttaque() {}
	
	public ActionAttaque (TypeCible typeCible, Integer valeur) throws ValeurNegativeException {
		super(typeCible);
		if(valeur < 0) { 
				throw new ValeurNegativeException("Les dégâts de l'attaque ne peuvent pas être négatifs");
		}
		this.valeur = valeur;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Inflige des dégats à toutes les cibles touchées par l'attaque
	 */
	@Override
	void executer(Jeu jeu) {
		List<Ciblable> cibles = jeu.recupererCibles(this.getTypeCible());
		for(Ciblable cible : cibles) {
			cible.prendreDegats(valeur);
		}
	}
}
