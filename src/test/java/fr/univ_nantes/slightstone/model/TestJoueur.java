package fr.univ_nantes.slightstone.model;

public class TestJoueur {

	public void construireJoueurAvecClasseGuerrier() {
		// When
		Joueur joueur = new Joueur("julien", ClasseHeros.GUERRIER);
		// Then
		assert (joueur.getPseudo().equals("julien"));
		assert (joueur.getMainJoueur().size() == 0);
		assert (joueur.getServiteurs().size() == 0);
		assert (joueur.getHeros().getPointsDeVie() == 30);
		assert (joueur.getHeros().getPointsArmure() == 0);
		assert (joueur.getPioche().stream().allMatch( carte -> {
			return carte.getClasse().equals(ClasseHeros.COMMUN) ||
				   carte.getClasse().equals(ClasseHeros.GUERRIER);
		}));
	}
	
	public void piocherCarte() { // TODO je suppose
		
	}
}
