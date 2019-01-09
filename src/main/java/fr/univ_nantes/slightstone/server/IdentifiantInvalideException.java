package fr.univ_nantes.slightstone.server;

public class IdentifiantInvalideException extends Exception {

	private static final long serialVersionUID = 4645904031667726683L;

	public IdentifiantInvalideException() {
		super();
	}
	
	public IdentifiantInvalideException(String message) {
		super(message);
	}
}
