package fr.univ_nantes.slightstone.controller;

import fr.univ_nantes.slightstone.model.*;

public class Controleur {
	
	private Jeu jeu;
	
	public Controleur(Jeu jeu) {
		this.jeu = jeu;
	}

	public void jouerCarte(Joueur joueur, DescripteurCarte carte) {
		if(this.jeu.estJoueurCourant(joueur) && 
				joueur.peutJoueurCarte(carte)) {
			this.jeu.jouerCarte(carte);
		} else {
			System.out.println("Ne peut pas jouer cette carte !");
		}
	}
		
	public void jouerCarteSort(Joueur joueur, DescripteurSort sort, Ciblable cible) {
		if(this.jeu.estJoueurCourant(joueur) && 
				joueur.peutJoueurCarte(sort) && 
				this.jeu.cibleCouranteValide(sort.cibleASelectionner())) {
			this.jeu.jouerCarte(sort, cible);
		} else {
			System.out.println("Ne peut pas jouer cette carte !");
		}
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
	
	public void attaquer(Joueur joueur, CarteServiteur serviteur, Ciblable cible) {
		System.out.println("EST CIBLE HEROS : " + (cible instanceof Heros));
		if(this.jeu.estJoueurCourant(joueur) &&
				joueur.possedeServiteur(serviteur) &&
				serviteur.estJouable() &&
				this.estCibleValidePourServiteur(cible)) {
			if(joueur.aServiteurAvecProvocation() && this.aProvocation(cible)) {
				this.jeu.attaquer(serviteur, cible);
			} else if(!joueur.aServiteurAvecProvocation()) {
				this.jeu.attaquer(serviteur, cible);
			} else {
				System.out.println("CIBLE AVEC PROVOC NON CIBLEE");
			}
		} else {
			System.out.println("PRE CONDITIONS INVALIDES");
		}
	}

	public void lancerActionHeros(Joueur joueur) {
		if(this.jeu.estJoueurCourant(joueur)) {
			this.jeu.lancerActionHeros();
		}		
	}

	public void lancerActionHeros(Joueur joueur, Ciblable cible) {
		DescripteurSort actionHeros = joueur.getHeros().getActionSpeciale();
		if(this.jeu.estJoueurCourant(joueur) && 
				this.jeu.cibleCouranteValide(actionHeros.cibleASelectionner())) {
			if(joueur.aServiteurAvecProvocation() && this.aProvocation(cible)) {
				this.jeu.lancerActionHeros(cible);
			} else if(!joueur.aServiteurAvecProvocation()) {
				this.jeu.lancerActionHeros(cible);
			}
		} 
	}

	public void terminerTour(Joueur joueur) {
		if(this.jeu.estJoueurCourant(joueur)) {
			this.jeu.terminerTour();
		}
	}
	
	public boolean estJeuTermine() {
		return this.jeu.estTermine();
	}
	
	public Joueur getVainqueuer() {
		return this.jeu.getJoueurCourant();
	}
}
