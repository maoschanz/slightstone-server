package fr.univ_nantes.slightstone.server.messages;

public class MessageFinPartie {

	private String pseudoVainqueur;
	
	public MessageFinPartie(String pseudoVainqueur) {
		this.pseudoVainqueur = pseudoVainqueur;
	}
	
	public String getPseudoVainqueur() {
		return this.pseudoVainqueur;
	}
}
