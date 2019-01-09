package fr.univ_nantes.slightstone.server;

public class MessageAttaquer {

	private Integer idServiteur;
	private Integer idCible;
	
	public MessageAttaquer() {
	}
	
	public MessageAttaquer(Integer idCarte, Integer idCible) {
		this.idServiteur = idCarte;
		this.idCible = idCible;
	}
	
	public Integer getIdServiteur() {
		return this.idServiteur;
	}
	
	public void setIdServiteur(Integer idCarte) {
		this.idServiteur = idCarte;
	}
	
	public Integer getIdCible() {
		return this.idCible;
	}
	
	public void setIdCible(Integer idCible) {
		this.idCible = idCible;
	}
}
