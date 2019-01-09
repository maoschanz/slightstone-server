package fr.univ_nantes.slightstone.server;

public class MessageErreur {

	private Integer code;
	private String message;
	
	public MessageErreur(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
