package fr.univ_nantes.slightstone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtatPartie {

	private Jeu jeu;
	
	public EtatPartie(Jeu jeu) {
		this.jeu = jeu;
	}
	
	private Map<String, Object> getActionSpecialeHeros(Heros heros) {
		DescripteurSort actionHeros = heros.getActionSpeciale();
		Map<String, Object> actionSpeciale = new HashMap<String, Object>();
		actionSpeciale.put("nom", actionHeros.getNom());
		actionSpeciale.put("description", actionHeros.getDescription());
		actionSpeciale.put("image", actionHeros.getimageURL());
		actionSpeciale.put("cout", actionHeros.getCoutMana());
		return actionSpeciale;
	}
	
	private Map<String, Object> getEtatHeros(Joueur joueur, boolean estAdversaire) {
		Heros heros = joueur.getHeros();
		Map<String, Object> etatHeros = new HashMap<String, Object>();
		etatHeros.put("classe", heros.getDescripteur().getClasse().toString());
		etatHeros.put("image", heros.getDescripteur().getUrlImage());
		etatHeros.put("pointsVie", heros.getPointsDeVie());
		etatHeros.put("pointsArmure", heros.getPointsArmure());
		if(!estAdversaire) {
			etatHeros.put("actionSpeciale", this.getActionSpecialeHeros(heros));
		}
		return etatHeros;
	}
	
	private HashMap<String, Object> getStockManaJoueur(Joueur joueur) {
		HashMap<String, Object> stockMana = new HashMap<String, Object>();
		stockMana.put("quantite", joueur.getQuantiteMana());
		stockMana.put("capacite", joueur.getCapaciteMana());
		return stockMana;
	}
	
	private HashMap<String, Object> getDescriptionServiteur(DescripteurServiteur descServiteur) {
		HashMap<String, Object> descripteur = new HashMap<String, Object>();
		descripteur.put("nom", descServiteur.getNom());
		descripteur.put("description", descServiteur.getDescription());
		descripteur.put("image", descServiteur.getimageURL());
		descripteur.put("classe", descServiteur.getClasse());
		descripteur.put("cout", descServiteur.getCoutMana());
		descripteur.put("degats", descServiteur.getPointsDeDegats());
		descripteur.put("vie", descServiteur.getPointsDeVie());
		descripteur.put("effetProvocation", descServiteur.aEffetProvocation());
		descripteur.put("effetLeader", descServiteur.aEffetLeader());
		descripteur.put("effetCharge", descServiteur.aEffetCharge());
		descripteur.put("effetVolDeVie", descServiteur.aEffetVolDeVie());
		return descripteur;
	}
	
	private List<Map<String, Object>> getServiteursMain(Joueur joueur) {
		List<Map<String, Object>> serviteurs = new ArrayList<Map<String, Object>>();
		for(DescripteurCarte descripteurCarte : joueur.getMainJoueur()) {
			if(descripteurCarte instanceof DescripteurServiteur) {
				serviteurs.add(this.getDescriptionServiteur((DescripteurServiteur) descripteurCarte));
			}
		}
		return serviteurs;
	}
	
	private Map<String, Object> getDescriptionSort(DescripteurSort descSort) {
		HashMap<String, Object> descripteur = new HashMap<String, Object>();
		descripteur.put("nom", descSort.getNom());
		descripteur.put("description", descSort.getDescription());
		descripteur.put("image", descSort.getimageURL());
		descripteur.put("classe", descSort.getClasse());
		descripteur.put("cout", descSort.getCoutMana());
		descripteur.put("cible", descSort.cibleASelectionner());
		return descripteur;
	}
	
	private List<Map<String, Object>> getSortsMain(Joueur joueur) {
		List<Map<String, Object>> sorts = new ArrayList<Map<String, Object>>();
		for(DescripteurCarte descripteurCarte : joueur.getMainJoueur()) {
			if(descripteurCarte instanceof DescripteurSort) {
				sorts.add(this.getDescriptionSort((DescripteurSort) descripteurCarte));
			}
		}
		return sorts;
	}
	
	private Map<String, Object> getEtatMain(Joueur joueur) {
		HashMap<String, Object> etatMain = new HashMap<String, Object>();
		etatMain.put("serviteurs", this.getServiteursMain(joueur));
		etatMain.put("sorts", this.getSortsMain(joueur));
		return etatMain;
	}
	
	private Map<String, Object> getEtatServiteur(CarteServiteur serviteur) {
		HashMap<String, Object> etatServiteur = this.getDescriptionServiteur(serviteur.getDescripteur());
		etatServiteur.put("degats", serviteur.getPointsDeDegats());
		etatServiteur.put("vie", serviteur.getPointsDeVie());
		etatServiteur.put("effetProvocation", serviteur.aEffetProvocation());
		etatServiteur.put("effetLeader", serviteur.aEffetLeader());
		etatServiteur.put("effetCharge", serviteur.aEffetCharge());
		etatServiteur.put("effetVolDeVie", serviteur.aEffetVolDeVie());
		return etatServiteur;
	}
	
	private List<Map<String, Object>> getEtatBoard(Joueur joueur) {
		List<Map<String, Object>> serviteurs = new ArrayList<Map<String, Object>>();
		for(CarteServiteur serviteur : joueur.getServiteurs()) {
			serviteurs.add(getEtatServiteur(serviteur));
		}
		return serviteurs;
	}
	
	private Map<String, Object> getEtatJoueur(Joueur joueur, boolean estAdversaire) {
		HashMap<String, Object> etatJoueur = new HashMap<String, Object>();
		etatJoueur.put("pseudo", joueur.getPseudo());
		etatJoueur.put("heros", this.getEtatHeros(joueur, estAdversaire));
		etatJoueur.put("mana", this.getStockManaJoueur(joueur));
		etatJoueur.put("board", this.getEtatBoard(joueur));
		if(estAdversaire) {
			etatJoueur.put("nbCartesMain", joueur.getMainJoueur().size());
		} else {
			etatJoueur.put("main", this.getEtatMain(joueur));
		}
		return etatJoueur;
	}
	
	public Map<String, Object> getEtatPartie(Joueur joueur, Joueur adversaire) {
		HashMap<String, Object> etatPartie = new HashMap<String, Object>();
		etatPartie.put("joueurCourant", this.jeu.getJoueurCourant().getPseudo());
		etatPartie.put("joueur", this.getEtatJoueur(joueur, false));
		etatPartie.put("adversaire", this.getEtatJoueur(adversaire, true));
		return etatPartie;
	}
}
