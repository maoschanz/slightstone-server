package fr.univ_nantes.slightstone.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBoard {
	private DescripteurServiteur descServiteurAvecEffetLeader;
	private DescripteurServiteur descServiteurAvecEffetCharge;
	private DescripteurServiteur descServiteurSansEffet;

	@BeforeEach
	public void initAll() {
		this.descServiteurAvecEffetLeader = new DescripteurServiteur("dragon",
				"un dragon", "dragon.png", ClasseHeros.PALADIN,
				2, 3, 5, false, false, false, true);
		this.descServiteurAvecEffetCharge = new DescripteurServiteur("dragon",
				"un dragon", "dragon.png", ClasseHeros.PALADIN,
				2, 3, 5, false, true, false, false);
		this.descServiteurSansEffet = new DescripteurServiteur("chevalier",
				"un chevalier", "chevalier.png", ClasseHeros.PALADIN,
				2, 3, 5, false, false, false, false);
	}

	@Test
	public void transmettreEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		// When
		board.transmettreEffetLeader();
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 1);
		}
	}

	@Test
	public void recupererEffetLeaderQuandAucunServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurInvoque = new CarteServiteur(this.descServiteurAvecEffetLeader);
		// When
		board.recupererEffetLeader(serviteurInvoque);
		// Then
		assert (serviteurInvoque.getPointsDeDegats() == serviteurInvoque.getDescripteur().getPointsDeDegats());
	}

	@Test
	public void recupererEffetLeaderQuandUnServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetLeader);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurInvoque = new CarteServiteur(this.descServiteurAvecEffetLeader);
		// When
		board.recupererEffetLeader(serviteurInvoque);
		// Then
		assert (serviteurInvoque.getPointsDeDegats() == serviteurInvoque.getDescripteur().getPointsDeDegats() + 1);
	}

	@Test
	public void recupererEffetLeaderQuandPlusieursServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		int n = 6; // nombre de serviteurs avec l'effet "Leader"
		for (int i = 0; i < n; i++) {
			board.invoquer(this.descServiteurAvecEffetLeader);
		}
		CarteServiteur serviteurInvoque = new CarteServiteur(this.descServiteurAvecEffetLeader);
		// When
		board.recupererEffetLeader(serviteurInvoque);
		// Then
		assert (serviteurInvoque.getPointsDeDegats() == serviteurInvoque.getDescripteur().getPointsDeDegats() + n);
	}

	@Test
	public void invoquerServiteurAvecEffetLeaderEtAucunServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		// When
		board.invoquer(this.descServiteurAvecEffetLeader);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 1);
		}
	}

	@Test
	public void invoquerServiteurAvecEffetLeaderEtUnServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurAvecEffetLeader);
		// When
		board.invoquer(this.descServiteurAvecEffetLeader);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 2);
		}
	}

	@Test
	public void invoquerServiteurAvecEffetLeaderEtPlusieursServiteursSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		int n = 6; // nombre de serviteurs avec l'effet "Leader"
		for (int i = 0; i < n; i++) {
			board.invoquer(this.descServiteurAvecEffetLeader);
		}
		// When
		board.invoquer(this.descServiteurAvecEffetLeader);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + (n + 1));
		}
	}

	@Test
	public void invoquerServiteurSansEffetLeaderEtAucunServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		// When
		board.invoquer(this.descServiteurSansEffet);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats());
		}
	}

	@Test
	public void invoquerServiteurSansEffetLeaderEtUnServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurAvecEffetLeader);
		// When
		board.invoquer(this.descServiteurSansEffet);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 1);
		}
	}

	@Test
	public void invoquerServiteurSansEffetLeaderEtPlusieursServiteurSurPlateauAvecEffetLeader() {
		// With
		Board board = new Board();
		int n = 6; // nombre de serviteurs avec l'effet "Leader"
		for (int i = 0; i < n; i++) {
			board.invoquer(this.descServiteurAvecEffetLeader);
		}
		// When
		board.invoquer(this.descServiteurSansEffet);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + n);
		}
	}

	@Test
	public void retirerCarteAvecEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetLeader);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurAvecEffetLeader = board.getCartes().get(0);
		assert (serviteurAvecEffetLeader.aEffetLeader());
		// When
		board.retirer(serviteurAvecEffetLeader);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats());
		}
	}

	@Test
	public void retirerCarteSansEffetLeader() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetLeader);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurSansEffetLeader = board.getCartes().get(1);
		assert (!serviteurSansEffetLeader.aEffetLeader());
		// When
		board.retirer(serviteurSansEffetLeader);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats() + 1);
		}
	}

	@Test
	public void siPasEffetChargeServiteurInvoqueInjouable() {
		// With
		Board board = new Board();
		// Then
		board.invoquer(this.descServiteurSansEffet);
		// Then
		CarteServiteur serviteurInvoque = board.getCartes().get(0);
		assert (!serviteurInvoque.estJouable());
	}

	@Test
	public void siEffetChargeServiteurInvoqueJouable() {
		// With
		Board board = new Board();
		// Then
		board.invoquer(this.descServiteurAvecEffetCharge);
		// Then
		CarteServiteur serviteurInvoque = board.getCartes().get(0);
		assert (serviteurInvoque.estJouable());
	}

	@Test
	public void actualiserJouabiliteServiteurs() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetCharge);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		// When
		board.actualiserJouabiliteServiteurs();
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.estJouable());
		}
	}

	@Test
	public void siServiteurMeurtServiteurRetirerPlateau() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetCharge);
		CarteServiteur serviteurInvoque = board.getCartes().get(0);
		// When
		serviteurInvoque.prendreDegats(serviteurInvoque.getPointsDeVie());
		// Then
		assert (board.getCartes().size() == 0);
	}

	@Test
	public void siServiteurAvecEffetLeaderMeurtReductionDegats() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetLeader);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurAvecEffetLeader = board.getCartes().get(0);
		assert (serviteurAvecEffetLeader.aEffetLeader());
		// When
		serviteurAvecEffetLeader.prendreDegats(serviteurAvecEffetLeader.getPointsDeVie());
		// Then
		assert (board.getCartes().size() == 2);
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats());
		}
	}

	@Test
	public void siServiteurAvecEffetLeaderPerdEffetLeaderAlorsReductionDegats() {
		// With
		Board board = new Board();
		board.invoquer(this.descServiteurAvecEffetLeader);
		board.invoquer(this.descServiteurSansEffet);
		board.invoquer(this.descServiteurSansEffet);
		CarteServiteur serviteurAvecEffetLeader = board.getCartes().get(0);
		assert (serviteurAvecEffetLeader.aEffetLeader());
		// When
		serviteurAvecEffetLeader.setEffetLeader(false);
		// Then
		for (CarteServiteur serviteur : board.getCartes()) {
			assert (serviteur.getPointsDeDegats() == serviteur.getDescripteur().getPointsDeDegats());
		}
	}
}
