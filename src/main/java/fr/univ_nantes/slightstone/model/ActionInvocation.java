package fr.univ_nantes.slightstone.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Cette action invoque un serviteur sur le plateau du joueur courant
 * Le serviteur invoqué est défini par l'attribut descServiteur 
 */
@Entity
@Table(name="action_invocation")
@PrimaryKeyJoinColumn(name="id_action")
public class ActionInvocation extends Action {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@OneToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="id_serviteur", nullable=false)
	private DescripteurServiteur descServiteur; //description du serviteur invoqué
		
	/* *********************************** */
	/* ********** Constructeurs ********** */
	/* *********************************** */
	
	protected ActionInvocation() {}
	
	public ActionInvocation (@NotNull DescripteurServiteur descServiteur) {
		super(TypeCible.AUCUNE); //cette action ne nécessite aucune cible
		this.descServiteur = descServiteur;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Invoque un serviteur sur le plateau du joueur
	 */
	@Override
	void executer(Jeu jeu) {
		Joueur joueurCourant = jeu.getJoueurCourant();
		joueurCourant.invoquerServiteur(this.descServiteur);
	}
}