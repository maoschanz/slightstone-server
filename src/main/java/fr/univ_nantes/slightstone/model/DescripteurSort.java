package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import fr.univ_nantes.slightstone.model.exceptions.ActionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe réunit toutes les informations concernant les statistiques d'un sort.
 */
@Entity
@Table(name="sorts")
@PrimaryKeyJoinColumn(name="id_carte")
public class DescripteurSort extends DescripteurCarte implements Cloneable {
	
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
	                        Integer coutMana,
	                        boolean piochable) {
		super(nom, description, imageURL, coutMana, classe, piochable);
		this.actions = new ArrayList<Action>();
	}

	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Vérifie si au moins une action requiert la sélection d'une cible.
	 * Si au moins une action requiert la sélection d'une cible, alors retourne
	 * le type de cible que doit sélectionner le joueur.
	 * Si aucune action ne requiert la sélection d'une cible, alors retourne
	 * TypeCible.AUCUNE (aucune cible à sélectionner).
	 * 
	 * @return : type de cible que doit sélectionner le joueur
	 */
	public TypeCible typeCibleAttendu() {
		for(Action action : this.actions) {
			if(action.requiertCible()) {
				return action.getTypeCible();
			}
		}
		return TypeCible.AUCUNE;
	}
	
	/**
	 * Vérifie si une action requiert la selection d'une cible.
	 * 
	 * @return : AUCUNE si le sort ne requiert aucune cible; UN_ADVERSAIRE,
	 * UN_SERVITEUR_ADVERSE, UN_SERVITEUR_ALLIE sinon
	 */
	public boolean requiertSelection() {
		for(Action action : this.actions) {
			if(action.requiertCible()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Ajoute une action aux actions de la carte sort.
	 * Une carte sort ne peut avoir au plus qu'une action qui requiert
	 * la sélection d'une cible par le joueur!
	 * 
	 * @param action : action à ajouter
	 * @throws ActionException : levé lorsqu'on souhaite ajouter plus d'une action
	 * qui requiert la sélection d'une cible par le joueur.
	 */
	public void ajouterAction(Action action) throws ActionException {
		if(this.requiertSelection()) {
			throw new ActionException("Une carte sort ne peut avoir au plus qu'une action qui nécessite une cible!");
		}
		this.actions.add(action);
	}
	
	/**
	 * Exécute toutes les actions associées à la carte sort.
	 */
	public void lancerActions(Jeu jeu) {
		actions.stream().forEach( action -> { action.executer(jeu); } );
	}
	
	@Override
	public Object clone() {
		Object o = null;
		o = super.clone();
		return o;
	}

	/**
	 * Indique si la carte correspond à une carte sort
	 * 
	 * @return : true
	 */
	@Override
	public boolean estSort() {
		return true;
	}

	/**
	 * Indique si la carte correspond à une carte serviteur
	 * 
	 * @return : false
	 */
	@Override
	public boolean estServiteur() {
		return false;
	}
}
