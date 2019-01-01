package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe réunit toutes les informations concernant les statistiques d'un sort. 
 */
@Entity
@Table(name="sorts")
@PrimaryKeyJoinColumn(name="id_carte")
public class DescripteurSort extends DescripteurCarte {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="id_action")
	private List<Action> actions;

	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */

	protected DescripteurSort() {}
	
	public DescripteurSort (String nom,
	                        String description,
	                        String imageURL,
	                        ClasseHeros classe,
	                        Integer coutMana) {
		super(nom, description, imageURL, coutMana, classe);
		this.actions = new ArrayList<Action>();
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Vérifie si une action requière la selection d'une cible.
	 * Si une action requière la sélection d'une cible, alors retourne
	 * le type de cible que doit sélectionner le joueur.
	 * Si aucune action requière la sélection d'une cible, alors retourne
	 * TypeCible.AUCUNE (aucune cible à sélectionner).
	 * 
	 * @return type de cible que doit sélectionner le joueur
	 */
	public TypeCible cibleASelectionner() {
		for(Action action : this.actions) {
			if(action.requiereCible()) {
				return action.typeCible;
			}
		}
		return TypeCible.AUCUNE;
	}
	
	/**
	 * Vérifie si une action requière la selection d'une cible.
	 * 
	 * @return
	 */
	public boolean requiereSelection() {
		for(Action action : this.actions) {
			if(action.requiereCible()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ajoute une action aux actions de la carte sort.
	 * Une carte sort ne peut avoir au plus qu'une action qui requière
	 * la sélection d'une cible par le joueur!
	 * 
	 * @param action : action à ajouter
	 * @throws ActionException : levé lorsqu'on souhaite ajouter plus d'une action
	 * qui requière la sélection d'une cible par le joueur.
	 */
	public void ajouterAction(Action action) throws ActionException {
		if(this.requiereSelection()) {
			throw new ActionException("Une carte sort ne peut avoir au plus qu'une action qui nécessite une cible!");
		}
		this.actions.add(action);
	}
	
	/**
	 * Exécute toutes les actions associées à la carte sort.
	 */
	public void lancerActions() {
		actions.stream().forEach( action -> { action.executer(); } );
	}
}