package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private ArrayList<CarteServiteur> serviteurs;

	public Board () {
		this.serviteurs = new ArrayList<CarteServiteur>();
	}

	public void poser (CarteServiteur serviteur) {
		this.serviteurs.add(serviteur);
		if(serviteur.aEffetLeader()) {
			this.serviteurs.stream().forEach(s -> { 
				s.boostDegats(1);; 
			});
		}
	}

	public void retirer (CarteServiteur serviteur) {
		this.serviteurs.remove(serviteur);
	}
	
	public List<CarteServiteur> getServiteurs() {
		return this.serviteurs;
	}
}