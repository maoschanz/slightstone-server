package fr.univ_nantes.slightstone.server;

import fr.univ_nantes.slightstone.model.ClasseHeros;

public class MessageLancerPartie {
	
	private String pseudo;
	private ClasseHeros heros;
	
	public MessageLancerPartie() {
	}
	
	public MessageLancerPartie(String pseudo, ClasseHeros heros) {
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
