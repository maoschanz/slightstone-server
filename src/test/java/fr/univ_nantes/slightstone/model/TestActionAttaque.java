package fr.univ_nantes.slightstone.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_nantes.slightstone.model.actions.Action;
import fr.univ_nantes.slightstone.model.actions.ActionAttaque;
import fr.univ_nantes.slightstone.model.exceptions.ValeurNegativeException;

public class TestActionAttaque {
	//private DescripteurHeros descHeros;
	private List<CarteServiteur> serviteursJ1;
	private Heros herosJ2;
	private List<CarteServiteur> serviteursJ2;
	private Jeu jeu;
	private DescripteurServiteur descServiteur;

	@BeforeEach
	public void initAll() {
		// Initialisation du jeu

		DescripteurServiteur descServiteur = new DescripteurServiteur("chevalier",
				"un chevalier", "chevalier.png", ClasseHeros.COMMUN,
				2, 1, 3, false, false, false, false);

		DescripteurHeros descHeros = mock(DescripteurHeros.class);
		when(descHeros.getPointsVie()).thenReturn(30);
		when(descHeros.getPointsArmure()).thenReturn(0);

		Joueur joueur1 = mock(Joueur.class);
		Heros herosJ1 = new Heros(descHeros);
		when(joueur1.getHeros()).thenReturn(herosJ1);
		List<CarteServiteur> serviteursJ1 = new ArrayList<CarteServiteur>();
		serviteursJ1.add(new CarteServiteur(descServiteur));
		serviteursJ1.add(new CarteServiteur(descServiteur));
		serviteursJ1.add(new CarteServiteur(descServiteur));
		when(joueur1.getServiteurs()).thenReturn(serviteursJ1);
		
		this.serviteursJ1 = serviteursJ1;

		Joueur joueur2 = mock(Joueur.class);
		Heros herosJ2 = new Heros(descHeros);
		when(joueur2.getHeros()).thenReturn(herosJ2);
		List<CarteServiteur> serviteursJ2 = new ArrayList<CarteServiteur>();
		serviteursJ2.add(new CarteServiteur(descServiteur));
		serviteursJ2.add(new CarteServiteur(descServiteur));
		serviteursJ2.add(new CarteServiteur(descServiteur));
		when(joueur2.getServiteurs()).thenReturn(serviteursJ2);
		
		this.herosJ2 = herosJ2;
		this.serviteursJ2 = serviteursJ2;

		this.jeu = new Jeu(joueur1, joueur2);
	}

	@Test
	public void construireActionAttaqueAvecDegatsNegatifsImpossible() {
		try {
			@SuppressWarnings("unused")
			ActionAttaque actionAttaque = new ActionAttaque(TypeCible.AUCUNE, -3);
			assert false;
		} catch (ValeurNegativeException e) {
			assert true;
		}
	}

	/*@Test
	public void recupererTousAdversaires() {
		try {
			// With
			ActionAttaque actionAttaque = new ActionAttaque(TypeCible.TOUS_ADVERSAIRES, 2);
			// When
			List<Ciblable> cibles = this.jeu.recupererCibles(actionAttaque.getTypeCible());
			// Then
			assert (cibles.size() == this.serviteursJ2.size() + 1);
			for (Ciblable cible : cibles) {
				if (cible instanceof CarteServiteur) {
					assert (this.serviteursJ2.contains(cible));
				} else if (cible instanceof Heros) {
					assert (cible.equals(this.herosJ2));
				} else {
					assert false;
				}
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void recupererServiteursAdverses() {
		try {
			// With
			ActionAttaque actionAttaque = new ActionAttaque(TypeCible.TOUS_SERVITEURS_ADVERSES, 2);
			// When
			List<Ciblable> cibles = this.jeu.recupererCibles(actionAttaque.getTypeCible());
			// Then
			assert (cibles.size() == this.serviteursJ2.size());
			for (Ciblable cible : cibles) {
				if (cible instanceof CarteServiteur) {
					assert (this.serviteursJ2.contains(cible));
				} else {
					assert false;
				}
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void recupererTousServiteurs() {	
		try {
			// With
			ActionAttaque actionAttaque = new ActionAttaque(TypeCible.TOUS_SERVITEURS, 2);
			// When
			List<Ciblable> cibles = this.jeu.recupererCibles(actionAttaque.getTypeCible()); 
			// Then
			assert (cibles.size() == this.serviteursJ1.size() + this.serviteursJ2.size());
			for (Ciblable cible : cibles) {
				if (cible instanceof CarteServiteur) {
					assert !(this.serviteursJ1.contains(cible) && this.serviteursJ2.contains(cible));
					assert (this.serviteursJ1.contains(cible) || this.serviteursJ2.contains(cible));
				} else {
					assert false;
				}
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}*/

	@Test
	public void executerActionAttaqueSurServiteurAdverse() {
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.UN_SERVITEUR_ADVERSE, 2);
			CarteServiteur cible = serviteursJ2.get(0);
			this.jeu.setCibleCourante(cible);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			assert (cible.getPointsDeVie() == cible.getDescripteur().getPointsDeVie() - 2);
			for (int i = 1; i < this.serviteursJ2.size(); i++) {
				CarteServiteur serviteurNonCible = this.serviteursJ2.get(i);
				assert (serviteurNonCible.getPointsDeVie() == serviteurNonCible.getDescripteur().getPointsDeVie());
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void executerActionAttaqueSurServiteursAdverses() {
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.TOUS_SERVITEURS_ADVERSES, 2);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			CarteServiteur serviteur;
			for (int i = 1; i < this.serviteursJ2.size(); i++) {
				serviteur = this.serviteursJ2.get(i);
				assert (serviteur.getPointsDeVie() == serviteur.getDescripteur().getPointsDeVie() - 2);
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void executerActionAttaqueSurTousServiteurs() {	
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.TOUS_SERVITEURS, 2);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			CarteServiteur serviteur;
			for (int i = 1; i < serviteursJ2.size(); i++) {
				serviteur = serviteursJ2.get(i);
				assert (serviteur.getPointsDeVie() == serviteur.getDescripteur().getPointsDeVie() - 2);
			}
			for (int i = 1; i < this.serviteursJ1.size(); i++) {
				serviteur = this.serviteursJ1.get(i);
				assert (serviteur.getPointsDeVie() == serviteur.getDescripteur().getPointsDeVie() - 2);
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void executerActionAttaqueSurTousAdversaires() {	
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.TOUS_ADVERSAIRES, 2);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			CarteServiteur serviteur;
			for (int i = 1; i < this.serviteursJ2.size(); i++) {
				serviteur = this.serviteursJ2.get(i);
				assert (serviteur.getPointsDeVie() == serviteur.getDescripteur().getPointsDeVie() - 2);
			}
			assert (this.herosJ2.getPointsDeVie() == this.herosJ2.getDescripteur().getPointsVie() - 2);
		} catch (ValeurNegativeException e) {
			assert false;
		}	
	}

	public void executerActionAttaqueAvecCibleHeros() {
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.UN_ADVERSAIRE, 2);
			Heros cible = this.herosJ2;
			this.jeu.setCibleCourante(cible);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			assert (cible.getPointsDeVie() == cible.getDescripteur().getPointsVie() - 2);
			for (int i = 1; i < this.serviteursJ2.size(); i++) {
				CarteServiteur serviteurNonCible = this.serviteursJ2.get(i);
				assert (serviteurNonCible.getPointsDeVie() == this.descServiteur.getPointsDeVie());
			}
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

	@Test
	public void executerActionAttaqueAvecCibleServiteur() {
		try {
			// With
			Action actionAttaque = new ActionAttaque(TypeCible.UN_ADVERSAIRE, 2);
			CarteServiteur cible = this.serviteursJ2.get(0);
			this.jeu.setCibleCourante(cible);
			// When
			actionAttaque.executer(this.jeu);
			// Then
			assert (cible.getPointsDeVie() == cible.getDescripteur().getPointsDeVie() - 2);
			for (int i = 1; i < this.serviteursJ2.size(); i++) {
				CarteServiteur serviteurNonCible = this.serviteursJ2.get(i);
				assert (serviteurNonCible.getPointsDeVie() == serviteurNonCible.getDescripteur().getPointsDeVie());
			}
			assert (this.herosJ2.getPointsDeVie() == this.herosJ2.getDescripteur().getPointsVie());
		} catch (ValeurNegativeException e) {
			assert false;
		}
	}

}
