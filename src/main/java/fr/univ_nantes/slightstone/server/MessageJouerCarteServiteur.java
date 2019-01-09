package fr.univ_nantes.slightstone.server;

public class MessageJouerCarteServiteur {

	private Integer idCarte;
	
	public MessageJouerCarteServiteur() {
	}
	
	public MessageJouerCarteServiteur(Integer idCarte) {
		this.idCarte = idCarte;
	}
	
	public Integer getIdCarte() {
		return this.idCarte;
	}
	
	public void setIdCarte(Integer idCarte) {
		this.idCarte = idCarte;
	}
}
