package fr.univ_nantes.slightstone.model;

/**
 * Cette interface permet de gérer de la même manière un héros et un serviteur
 * car tous deux sont sélectionnables et attaquables
 */
public interface Ciblable {
	void prendreDegats(Integer valeur);
	boolean aProvocation();
}
