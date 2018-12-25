package fr.univ_nantes.slightstone.server;

public interface IServeur{
	void jouerCarte(Integer idCarte);
	void jouerCarte(Integer idCarte, Integer idCible);
	void terminerTour();
	void lancerActionHeros();
	void lancerActionHeros(Integer idCible);
	void attaquer(Integer idServiteur, Integer idCible);
}