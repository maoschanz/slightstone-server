package fr.univ_nantes.slightstone.model.actions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import fr.univ_nantes.slightstone.model.Jeu;
import fr.univ_nantes.slightstone.model.TypeCible;

@Entity
@Table(name="actions")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Action {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@Id
	@NotNull
	@Column(name="id_action")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@Column(name="type_cible")
	@Enumerated(EnumType.STRING)
	private TypeCible typeCible; //utilisé pour récupérer la/les cible(s) souhaitée(s)
								   //permet de vérifier qu'une cible envoyée par l'utilisateur est correcte

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected Action() {}
	
	public Action(TypeCible typeCible) {
		this.typeCible = typeCible;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

	public TypeCible getTypeCible() {
		return this.typeCible;
	}
	
	/**
	 * Indique si une action nécessite que l'utilisateur sélectionne une cible
	 * @return
	 */
	public boolean requiereCible() {
		switch(this.typeCible) {
		case AUCUNE:
		case TOUS_ADVERSAIRES:
		case TOUS_SERVITEURS:
		case TOUS_SERVITEURS_ADVERSES:
			return false;
		case UN_ADVERSAIRE:
		case UN_SERVITEUR_ADVERSE:
		case UN_SERVITEUR_ALLIE:
			return true;
		default:
			return false;
		}
	}
	
	public abstract void executer(Jeu jeu);
}