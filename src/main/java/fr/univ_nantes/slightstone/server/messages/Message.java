package fr.univ_nantes.slightstone.server.messages;

public class Message {

	private String contenu;
	
	public Message(String contenu) {
		this.contenu = contenu;
	}
	
	public String getContenu() {
		return this.contenu;
	}
}
