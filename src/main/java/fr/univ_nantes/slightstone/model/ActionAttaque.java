package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Cette action inflige des dégâts à une ou plusieurs cibles lorsqu'elle est exécutée.
 * Les cibles touchées sont définis par le type TypeCible défini dans la sur-classe Action.
 * La valeur des dégats infligés à chaque cible est défini par l'attribut valeur.
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
	
	public ActionAttaque (Jeu jeu, TypeCible typeCible, Integer valeur) throws ValeurNegativeException {
		super(jeu, typeCible);
		if(valeur < 0) { 
				throw new ValeurNegativeException("Les dégâts de l'attaque ne peuvent pas être négatifs");
		}
		this.valeur = valeur;
	}
	
	/* ****************************** */
	/* ********** Méthodes ********** */
	/* ****************************** */
	
	/**
	 * Récupère les cibles touchées par l'attaque
	 * @return : liste des cibles
	 */
	protected List<Ciblable> recupererCibles() {
		List<Ciblable> cibles = new ArrayList<Ciblable>();
		switch(this.typeCible) {
		case TOUS_ADVERSAIRES:
			cibles.add(this.jeu.getJoueurAdverse().getHeros());
			cibles.addAll(this.jeu.getJoueurAdverse().getServiteurs());
			return cibles;
		case TOUS_SERVITEURS:
			cibles.addAll(this.jeu.getJoueurAdverse().getServiteurs());
			cibles.addAll(this.jeu.getJoueurCourant().getServiteurs());
			return cibles;
		case TOUS_SERVITEURS_ADVERSES:
			cibles.addAll(this.jeu.getJoueurAdverse().getServiteurs());
			return cibles;
		case UN_ADVERSAIRE:
		case UN_SERVITEUR_ADVERSE:
			cibles.add(this.jeu.getCibleCourante());
			return cibles;
		default:
			return cibles;
		}
	}
	
	/**
	 * Inflige des dégats à toutes les cibles touchées par l'attaque
	 */
	@Override
	public void executer() {
		List<Ciblable> cibles = this.recupererCibles();
		for(Ciblable cible : cibles) {
			cible.prendreDegats(valeur);
		}
	}
}