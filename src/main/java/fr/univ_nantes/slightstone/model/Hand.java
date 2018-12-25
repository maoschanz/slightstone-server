package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private ArrayList<DescripteurCarte> cartes;

	public Hand () {
		this.cartes = new ArrayList<DescripteurCarte>();
	}

	public void ajouter (DescripteurCarte carte) {
		this.cartes.add(carte);
	}

	public void retirer (DescripteurCarte carte) {
		this.cartes.remove(carte);
	}
	
	public List<DescripteurCarte> getCartes() {
		return this.cartes;
	}
}