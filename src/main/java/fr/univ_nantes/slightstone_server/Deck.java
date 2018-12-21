package fr.univ_nantes.slightstone_server;

import java.util.ArrayList;

public class Deck {
	private ArrayList<DescripteurCarte> cartes;

	public Deck () {
		this.cartes = new ArrayList<DescripteurCarte>();
	}

	public void ajouter (DescripteurCarte carte) {
		this.cartes.add(carte);
	}

	public DescripteurCarte piocher (Integer index) { //TODO cette méthode pourra être fusionnée avec l'autre
		DescripteurCarte carte = this.cartes.get(index);
		return carte;
	}

	public DescripteurCarte piocher () {
		Integer index = (int) Math.random()*this.cartes.size()-1;
		// if (piochable) { //TODO cet attribut fait polémique
			DescripteurCarte carte = this.piocher(index);
			return carte;
		// } else {
		// 	return this.piocher();
		// }
	}
}