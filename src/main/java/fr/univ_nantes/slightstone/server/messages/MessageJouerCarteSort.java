package fr.univ_nantes.slightstone.server.messages;

public class MessageJouerCarteSort {

	private Integer idCarte;
	private Integer idCible;
	
	public MessageJouerCarteSort() {
	}
	
	public MessageJouerCarteSort(Integer idCarte, Integer idCible) {
		this.idCarte = idCarte;
		this.idCible = idCible;
	}
	
	public Integer getIdCarte() {
		return this.idCarte;
	}
	
	public void setIdCarte(Integer idCarte) {
		this.idCarte = idCarte;
	}
	
	public Integer getIdCible() {
		return this.idCible;
	}
	
	public void setIdCible(Integer idCible) {
		this.idCible = idCible;
	}
}
