package fr.univ_nantes.slightstone.server.messages;

import fr.univ_nantes.slightstone.model.ClasseHeros;

public class MessageNouvellePartie {
	
	private String pseudo;
	private ClasseHeros heros;
	
	public MessageNouvellePartie() {
	}
	
	public MessageNouvellePartie(String pseudo, ClasseHeros heros) {
		this.pseudo = pseudo;
		this.heros = heros;
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public ClasseHeros getHeros() {
		return this.heros;
	}
	
	public void setHeros(ClasseHeros heros) {
		this.heros = heros;
	}
}
