package fr.univ_nantes.slightstone.controller;

import fr.univ_nantes.slightstone.model.*;

/**
 * Cette classe a pour rôle de manipuler le modèle tout en s'assurant que les règles du jeu soient respectées.
 * Les règles du jeu sont les suivantes :
 * 		- Un joueur ne peut jouer que lorsque c'est son tour
 * 		- Un joueur ne peut jouer qu'une carte qui est dans sa main
 * 		- Un joueur ne peut jouer une carte que s'il dispose de la quantité de mana suffisante
 * 		- Un joueur ne peut lancer l'action spécial du héros que s'il dispose de la quantité de mana suffisante
 * 		- Un joueur ne peut sélectionner une cible qui lui est interdite
 * 		- Un joueur ne peut attaquer avec un serviteur que s'il est invoqué sur le plateau
 * 		- Un joueur ne peut attaquer avec un serviteur que s'il est jouable
 * 		- Si un serviteur adverse a un effet provocation, le joueur doit attaquer ce serviteur
 *
 * En théorie, le client Angular de notre application réalise les contrôles nécessaires pour ne pas enfreindre ces règles.
 * Par conséquent, aucune des exceptions ci-dessous n'est censée être levée !
 */
public class Controleur {
	
	private Jeu jeu;
	
	public Controleur(Jeu jeu) {
		this.jeu = jeu;
	}
	
	private boolean aProvocation(Ciblable cible) {
		if(!(cible instanceof CarteServiteur)) {
			return false;
		}
		CarteServiteur serviteur = (CarteServiteur) cible;
		return serviteur.aEffetProvocation();
	}
	
	private boolean estCibleValidePourServiteur(Ciblable cible) {
		return this.jeu.getJoueurAdverse().getServiteurs().contains(cible) ||
				this.jeu.getJoueurAdverse().getHeros().equals(cible);
	}
	
	public boolean estJeuTermine() {
		return this.jeu.estTermine();
	}
	
	public Joueur getVainqueuer() {
		return this.jeu.getJoueurCourant();
	}

	public void jouerCarte(Joueur joueur, DescripteurCarte carte) throws ViolationReglesException {
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeCarte(carte)) {
			throw new ViolationReglesException("Un joueur ne peut jouer qu'une carte qui est dans sa main");
		} else if(!carte.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut jouer une carte que s'il dispose de la quantité de mana suffisante");
		}
		this.jeu.jouerCarte(carte);
	}
		
	public void jouerCarteSort(Joueur joueur, DescripteurSort sort, Ciblable cible) throws ViolationReglesException {
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeCarte(sort)) {
			throw new ViolationReglesException("Un joueur ne peut jouer qu'une carte qui est dans sa main");
		} else if(!sort.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut jouer une carte que s'il dispose de la quantité de mana suffisante");
		} else if(!this.jeu.cibleCouranteValide(sort.cibleASelectionner())) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		}
		this.jeu.jouerCarte(sort, cible);
	}
	
	public void attaquer(Joueur joueur, CarteServiteur serviteur, Ciblable cible) throws ViolationReglesException {
		Joueur adversaire = this.jeu.getJoueurAdverse();
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeServiteur(serviteur)) {
			throw new ViolationReglesException("Un joueur ne peut attaquer avec un serviteur que s'il est invoqué sur le plateau");
		} else if(!serviteur.estJouable()) {
			throw new ViolationReglesException("Un joueur ne peut attaquer avec un serviteur que s'il est jouable");
		} else if(!this.estCibleValidePourServiteur(cible)) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		} else if(adversaire.aServiteurAvecProvocation() && !this.aProvocation(cible)) {
			throw new ViolationReglesException("Si un serviteur adverse a un effet provocation, le joueur doit attaquer ce serviteur");
		}
		this.jeu.attaquer(serviteur, cible);
	}

	public void lancerActionHeros(Joueur joueur) throws ViolationReglesException {
		DescripteurSort actionHeros = joueur.getHeros().getActionSpeciale();
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!actionHeros.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut lancer l'action spécial du héros que s'il dispose de la quantité de mana suffisante");
		}
		this.jeu.lancerActionHeros();	
	}

	public void lancerActionHeros(Joueur joueur, Ciblable cible) throws ViolationReglesException {
		Joueur adversaire = this.jeu.getJoueurAdverse();
		DescripteurSort actionHeros = joueur.getHeros().getActionSpeciale();
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!actionHeros.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut lancer l'action spécial du héros que s'il dispose de la quantité de mana suffisante");
		} else if(!this.jeu.cibleCouranteValide(actionHeros.cibleASelectionner())) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		} else if(adversaire.aServiteurAvecProvocation() && !this.aProvocation(cible)) {
			throw new ViolationReglesException("Si un serviteur adverse a un effet provocation, le joueur doit attaquer ce serviteur");
		}
		this.jeu.lancerActionHeros(cible);
	}

	public void terminerTour(Joueur joueur) throws ViolationReglesException {
		if(!this.jeu.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		}
		this.jeu.terminerTour();
	}
}
