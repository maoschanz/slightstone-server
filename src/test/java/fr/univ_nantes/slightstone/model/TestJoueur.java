package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

import static org.mockito.Mockito.*;

public class TestJoueur {
	private Joueur joueur;

	@BeforeEach
	public void initAll() {
		this.joueur = new Joueur("Julien", ClasseHeros.MAGE);
	}

	@Test
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

	@Test
	public void piocherCarte() {
		// When
		this.joueur.piocherCarte();
		// Then
		assert (this.joueur.getMainJoueur().stream().allMatch( carte -> {
			return carte.getClasse().equals(ClasseHeros.COMMUN) ||
				   carte.getClasse().equals(ClasseHeros.MAGE);
		}));
	}
	
	@Test
	public void invoquerServiteur() {
		// When
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		this.joueur.invoquerServiteur(descServiteur);
		// Then
		assert (this.joueur.getServiteurs().stream().anyMatch( carte -> {
			return carte.getDescripteur().equals(descServiteur);
		}));
	}
	
	@Test
	public void jouerCarte() {
		// With
		Integer mana = this.joueur.getQuantiteMana();
		Joueur joueur2 = new Joueur("double maléfique de Julien", ClasseHeros.MAGE);
		Jeu jeu = new Jeu(this.joueur, joueur2);
		jeu.initialiserMainJoueurs();
		DescripteurCarte dcarte = this.joueur.getMainJoueur().get(0);
		// When
		this.joueur.jouerCarte(jeu, dcarte);
		// Then
		assert (mana == this.joueur.getQuantiteMana() + 3); // la mana baisse
		assert (this.joueur.getMainJoueur().size() == 2); // la carte sort de la main
		if (dcarte instanceof DescripteurSort) { // si c'est un sort on en lance les actions
//			assert (this.joueur.); // XXX très pénible à vérifier
		} else { // si c'est un serviteur on l'invoque
			assert (this.joueur.getServiteurs().stream().anyMatch( carte -> {
				return carte.getDescripteur().equals(dcarte);
			}));
		}
	}
	
	@Test
	public void jouerActionHeros() {
		// With
		Integer mana = this.joueur.getQuantiteMana();
		Joueur joueur2 = new Joueur("double maléfique de Julien", ClasseHeros.MAGE);
		Jeu jeu = new Jeu(this.joueur, joueur2);
		// When
		this.joueur.jouerActionHeros(jeu);
		// Then
		assert (mana == this.joueur.getQuantiteMana() + this.joueur.getHeros().getCoutActionSpeciale());
	}
	
	@Test
	public void augmenterCapaciteMana() {
		// With
		Integer mana = this.joueur.getCapaciteMana();
		// When
		this.joueur.augmenterCapaciteMana();
		// Then
		assert (mana + 1 == this.joueur.getCapaciteMana());
	}
	
	@Test
	public void actualiserJouabiliteServiteurs() {
		// When
		this.joueur.actualiserJouabiliteServiteurs();
		// Then
		assert (this.joueur.getServiteurs().stream().allMatch( carte -> {
			return carte.estJouable();
		}));
	}
	
	@Test
	public void possedeCarte() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		// When
		boolean b = this.joueur.possedeCarte(descServiteur);
		// Then
		assert (b = this.joueur.getMainJoueur().contains(descServiteur));
	}
	
	@Test
	public void possedeServiteur() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		CarteServiteur serv = new CarteServiteur(descServiteur);
		// When
		boolean b = this.joueur.possedeServiteur(serv);
		// Then
		assert (b = this.joueur.getServiteurs().contains(serv));
	}
	
	@Test
	public void aServiteurAvecProvocation() {
		// With
		DescripteurServiteur descServiteur1 = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, false, false, false, true);
		// When
		this.joueur.invoquerServiteur(descServiteur1);
		// Then
		assert (!this.joueur.aServiteurAvecProvocation());
		
		// With
		DescripteurServiteur descServiteur2 = new DescripteurServiteur("Chef de raid",
				"Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN,
				3, 2, 2, true, false, false, true);
		// When
		this.joueur.invoquerServiteur(descServiteur2);
		// Then
		assert (this.joueur.aServiteurAvecProvocation());
	}
}
