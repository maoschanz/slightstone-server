package fr.univ_nantes.slightstone.controller;

import java.util.List;

import fr.univ_nantes.slightstone.model.*;

public class Controleur {
	/*
	private Jeu jeu;
	
	public Controleur(Jeu jeu) {
		this.jeu = jeu;
	}
	
	public boolean peutJouerJoueur(Joueur joueur) {
		return this.jeu.getJoueurCourant().equals(joueur);
	}
	
	private boolean cibleValide(TypeCible typeCible, Ciblable cible) {
		switch(typeCible) {
		case UN_ADVERSAIRE:
			return this.jeu.getJoueurAdverse().getServiteurs().contains(cible) ||
					this.jeu.getJoueurAdverse().getHeros().equals(cible);
		case UN_SERVITEUR_ADVERSE:
			return this.jeu.getJoueurAdverse().getServiteurs().contains(cible);
		case UN_SERVITEUR_ALLIE:
			return this.jeu.getJoueurCourant().getServiteurs().contains(cible);
		default:
			return false;
		}
	}
	
	public void jouerCarte(DescripteurCarte carte) {
		if(!this.peutJouerCarte(carte)) {
			// cette carte n'est pas dans la main du joueur !
		} else {
			this.jeu.jouerCarte(carte);
		}
	}
	
	private boolean peutJouerCarte(DescripteurCarte carte) {
		return this.jeu.getJoueurCourant().getMainJoueur().contains(carte);
	}
	
	public void jouerCarte(DescripteurCarte carte, Ciblable cible) {
		if(carte instanceof DescripteurServiteur) {
			//pas possible, seules les cartes sorts ont besoin d'une cible
		}
		DescripteurSort sort = (DescripteurSort) carte;
		TypeCible typeCible = sort.cibleASelectionner();
		if(!this.peutJouerCarte(carte)) {
			//le joueur ne possède pas cette carte dans sa main
		} else if(!this.cibleValide(typeCible, cible)) {
			//cible invalide
		} else {
			this.jouerCarte(carte, cible);
		}
	}

	private boolean existeServiteurAdverseAvecProvocation() {
		List<CarteServiteur> serviteursAdverses = this.jeu.getJoueurAdverse().getServiteurs();
		for(CarteServiteur serviteur : serviteursAdverses) {
			if(serviteur.aEffetProvocation()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean aCibleProvocation(Ciblable cible) {
		if(cible instanceof Heros) {
			return false;
		}
		CarteServiteur serviteur = (CarteServiteur) cible;
		return serviteur.aEffetProvocation();
	}
	
	public void attaquer(CarteServiteur attaquant, Ciblable cible) {
		if(!attaquant.estJouable()) {
			//la carte n'est pas jouable
		} else if(!this.cibleValide(TypeCible.UN_ADVERSAIRE, cible)) {
			//cible invalide
		} else if(this.existeServiteurAdverseAvecProvocation() && !this.aCibleProvocation(cible)) {
			//obligation de cibler le serviteur avec une provocation
		} else {
			this.jeu.attaquer(attaquant, cible);
		}
	}

	public void lancerActionHeros() {
		this.jeu.lancerActionHeros();		
	}

	public void lancerActionHeros(Ciblable cible) {
		TypeCible typeCible = this.jeu.getJoueurCourant().getHeros().getTypeCibleActionSpeciale();
		if(!this.cibleValide(typeCible, cible)) {
			//cible invalide
		} else if(this.existeServiteurAdverseAvecProvocation() && !this.aCibleProvocation(cible)) {
			//obligation de cibler le serviteur avec une provocation
		} else {
			this.jeu.lancerActionHeros(cible);
		}
	}

	public void terminerTour() {
		// TODO Auto-generated method stub
	}*/
}
