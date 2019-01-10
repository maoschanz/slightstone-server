package fr.univ_nantes.slightstone.server.messages;

public class MessageLancerActionSpecial {

	private Integer idCible;
	
	public MessageLancerActionSpecial() {
	}
	
	public MessageLancerActionSpecial(Integer idCible) {
		this.idCible = idCible;
	}
	
	public Integer getIdCible() {
		return this.idCible;
	}
	
	public void setIdCible(Integer idCible) {
		this.idCible = idCible;
	}
}
