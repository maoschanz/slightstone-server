package fr.univ_nantes.slightstone_server;

import java.util.ArrayList;

public class Board extends ArrayList<CarteServiteur> {
	private ArrayList<CarteServiteur> serviteurs;

	public Board () {
		this.serviteurs = new ArrayList<CarteServiteur>();
	}

	public void ajouter (CarteServiteur serviteur) {
		this.serviteurs.add(serviteur);
	}

	public void retirer (CarteServiteur serviteur) {
		this.serviteurs.remove(serviteur);
	}
}