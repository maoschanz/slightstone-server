package fr.univ_nantes.slightstone_server;

import java.util.ArrayList;

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
}