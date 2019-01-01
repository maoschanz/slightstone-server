package fr.univ_nantes.slightstone.model;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDeck {

	@Test
	public void deckMageComposeDeCartesMageOuCommun() {
		Deck deck = new Deck(ClasseHeros.MAGE);
		List<DescripteurCarte> cartes = deck.getCartes();
		for(DescripteurCarte carte : cartes) {
			assert (ClasseHeros.MAGE.equals(carte.getClasse()) || ClasseHeros.COMMUN.equals(carte.getClasse()));
		}
	}
	
	@Test
	public void deckGuerrierComposeDeCartesGuerrierOuCommun() {
		Deck deck = new Deck(ClasseHeros.GUERRIER);
		List<DescripteurCarte> cartes = deck.getCartes();
		for(DescripteurCarte carte : cartes) {
			assert (ClasseHeros.GUERRIER.equals(carte.getClasse()) || ClasseHeros.COMMUN.equals(carte.getClasse()));
		}
	}
	
	@Test
	public void deckPaladinComposeDeCartesPaladinOuCommun() {
		Deck deck = new Deck(ClasseHeros.PALADIN);
		List<DescripteurCarte> cartes = deck.getCartes();
		for(DescripteurCarte carte : cartes) {
			assert (ClasseHeros.PALADIN.equals(carte.getClasse()) || ClasseHeros.COMMUN.equals(carte.getClasse()));
		}
	}
}
