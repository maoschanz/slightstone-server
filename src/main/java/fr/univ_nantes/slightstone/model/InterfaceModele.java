package fr.univ_nantes.slightstone.model;

import fr.univ_nantes.slightstone.model.exceptions.ViolationReglesException;

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
public class InterfaceModele {
	
	private Jeu jeu;
	
	public InterfaceModele(Jeu jeu) {
		this.jeu = jeu;
		jeu.initialiserMainJoueurs();
	}
	
	public Jeu getJeu() {
		return this.jeu;
	}
	
	/**
	 * Retourne le vainqueur de la partie
	 * 
	 * @return : le joueur dont le héros n'est pas mort
	 * @throws ViolationReglesException : levée si la partie n'est pas terminée
	 */
	public Joueur getVainqueur() throws ViolationReglesException {
		if(!this.jeu.estTermine()) {
			throw new ViolationReglesException("Il n'y a pas de vainqueur si le jeu n'est pas terminé");
		}
		return this.jeu.getJoueurCourant();
	}
	
	/**
	 * Vérifie si un joueur est le joueur courant (i.e. c'est à lui de jouer)
	 * 
	 * @param joueur : l'un des deux joueurs de la partie
	 * @return : true si le joueur est le joueur courant; false sinon
	 */
	public boolean estJoueurCourant(Joueur joueur) {
		return this.jeu.getJoueurCourant().equals(joueur);
	}

	public void jouerCarte(Joueur joueur, DescripteurCarte carte) throws ViolationReglesException {
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeCarte(carte)) {
			throw new ViolationReglesException("Un joueur ne peut jouer qu'une carte qui est dans sa main");
		} else if(!carte.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut jouer une carte que s'il dispose de la quantité de mana suffisante");
		}
		this.jeu.jouerCarte(carte);
	}
		
	public void jouerCarteSort(Joueur joueur, DescripteurSort sort, Ciblable cible) throws ViolationReglesException {
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeCarte(sort)) {
			throw new ViolationReglesException("Un joueur ne peut jouer qu'une carte qui est dans sa main");
		} else if(!sort.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut jouer une carte que s'il dispose de la quantité de mana suffisante");
		} else if(!this.jeu.estCibleValide(sort.typeCibleAttendu(), cible)) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		}
		this.jeu.jouerCarte(sort, cible);
	}
	
	public void attaquer(Joueur joueur, CarteServiteur serviteur, Ciblable cible) throws ViolationReglesException {
		Joueur adversaire = this.jeu.getJoueurAdverse();
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!joueur.possedeServiteur(serviteur)) {
			throw new ViolationReglesException("Un joueur ne peut attaquer avec un serviteur que s'il est invoqué sur le plateau");
		} else if(!serviteur.estJouable()) {
			throw new ViolationReglesException("Un joueur ne peut attaquer avec un serviteur que s'il est jouable");
		} else if(!this.jeu.estCibleValide(serviteur.typeCibleAttendu(), cible)) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		} else if(adversaire.aServiteurAvecProvocation() && !cible.aProvocation()) {
			throw new ViolationReglesException("Si un serviteur adverse a un effet provocation, le joueur doit attaquer ce serviteur");
		}
		this.jeu.attaquer(serviteur, cible);
	}

	public void lancerActionHeros(Joueur joueur) throws ViolationReglesException {
		Heros heros = joueur.getHeros();
		DescripteurSort actionHeros = heros.getActionSpeciale();
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!heros.estActionChargee()) {
			throw new ViolationReglesException("Un joueur ne peut jouer l'action spéciale du héros qu'une fois par tour");
		} else if(!actionHeros.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut lancer l'action spéciale du héros que s'il dispose de la quantité de mana suffisante");
		}
		this.jeu.lancerActionHeros();	
	}

	public void lancerActionHeros(Joueur joueur, Ciblable cible) throws ViolationReglesException {
		Joueur adversaire = this.jeu.getJoueurAdverse();
		Heros heros = joueur.getHeros();
		DescripteurSort actionHeros = heros.getActionSpeciale();
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		} else if(!heros.estActionChargee()) {
			throw new ViolationReglesException("Un joueur ne peut jouer l'action spéciale du héros qu'une fois par tour");
		} else if(!actionHeros.estManaSuffisante(joueur.getQuantiteMana())) {
			throw new ViolationReglesException("Un joueur ne peut lancer l'action spéciale du héros que s'il dispose de la quantité de mana suffisante");
		} else if(!this.jeu.estCibleValide(actionHeros.typeCibleAttendu(), cible)) {
			throw new ViolationReglesException("Un joueur ne peut sélectionner une cible qui lui est interdite");
		} else if(adversaire.aServiteurAvecProvocation() && !cible.aProvocation()) {
			throw new ViolationReglesException("Si un serviteur adverse a un effet provocation, le joueur doit attaquer ce serviteur");
		}
		this.jeu.lancerActionHeros(cible);
	}

	public void terminerTour(Joueur joueur) throws ViolationReglesException {
		if(!this.estJoueurCourant(joueur)) {
			throw new ViolationReglesException("Un joueur ne peut jouer que lorsque c'est son tour");
		}
		this.jeu.terminerTour();
	}
}
