package fr.univ_nantes.slightstone.server.messages;

import java.util.HashMap;

public class MessageEtatPartie {

	private HashMap<String, Object> etatPartie;
	
	public MessageEtatPartie(HashMap<String, Object> etatPartie) {
		this.etatPartie = etatPartie;
	}
	
	public HashMap<String, Object> getEtatPartie() {
		return this.etatPartie;
	}
}
