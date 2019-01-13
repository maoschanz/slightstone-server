package fr.univ_nantes.slightstone.model;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class TestActionInvocation {
	private Jeu jeu;
	
	@BeforeEach
	public void initAll() {
		// Initialisation du jeu
		Heros herosJ1 = mock(Heros.class);
		Deck deckJ1 = mock(Deck.class);
		Joueur joueur1 = new Joueur("julien", herosJ1, deckJ1, new MainJoueur(), new Board(), new StockMana());
		Heros herosJ2 = mock(Heros.class);
		Deck deckJ2 = mock(Deck.class);
		Joueur joueur2 = new Joueur("arthur", herosJ2, deckJ2, new MainJoueur(), new Board(), new StockMana());
		this.jeu = new Jeu(joueur1, joueur2);
	}
	
	@Test
	public void executerActionInvocation() {
		// With
		DescripteurServiteur descServiteur = new DescripteurServiteur("chevalier",
				"un chevalier", "chevalier.png", ClasseHeros.COMMUN,
				2, true, 1, 3, false, false, false, false);
		ActionInvocation actionInvocation = new ActionInvocation(descServiteur);
		//When
		actionInvocation.executer(this.jeu);
		//Then
		List<CarteServiteur> serviteursAllies = this.jeu.getJoueurCourant().getServiteurs();
		assert (serviteursAllies.size() == 1);
		CarteServiteur serviteurInvoque = serviteursAllies.get(0);
		assert (serviteurInvoque.getPointsDeVie() == descServiteur.getPointsDeVie());
		assert (serviteurInvoque.getPointsDeDegats() == descServiteur.getPointsDeDegats());
		assert (serviteurInvoque.aEffetLeader() == descServiteur.aEffetLeader());
		assert (serviteurInvoque.aEffetCharge() == descServiteur.aEffetCharge());
		assert (serviteurInvoque.aEffetProvocation() == descServiteur.aEffetProvocation());
		assert (serviteurInvoque.aEffetVolDeVie() == descServiteur.aEffetVolDeVie());
	}
}
