package fr.univ_nantes.slightstone.model;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
	protected TypeCible typeCible; //utilisé pour récupérer la/les cible(s) souhaitée(s)
								   //permet de vérifier qu'une cible envoyée par l'utilisateur est correcte
	
	@Transient
	protected Jeu jeu; //permet de récupérer les cibles des actions

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected Action() {}
	
	public Action(Jeu jeu, TypeCible typeCible) {
		this.jeu = jeu;
		this.typeCible = typeCible;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */

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
	
	public abstract void executer();
}