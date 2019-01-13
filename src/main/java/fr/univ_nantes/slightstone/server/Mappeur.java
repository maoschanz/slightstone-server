package fr.univ_nantes.slightstone.server;

import java.util.HashMap;

/**
 * Cette classe a pour rôle de faire le lien entre deux objets
 *
 * @param <C> type pour la clef
 * @param <V> type pour la valeur
 */
public class Mappeur<C, V> {

	private HashMap<C, V> clefVersValeur;
	private HashMap<V, C> valeurVersClef;
	
	public Mappeur(){
		this.clefVersValeur = new HashMap<C, V>();
		this.valeurVersClef = new HashMap<V, C>();
	}
	
	/**
	 * Ajoute un nouveau couple (clef, valeur) et (valeur, clef)
	 * 
	 * @param clef
	 * @param valeur
	 */
	public void put(C clef, V valeur) {
		this.clefVersValeur.put(clef, valeur);
		this.valeurVersClef.put(valeur, clef);
	}
	
	/**
	 * Retourne la valeur associée à une clef donnée
	 * 
	 * @param clef
	 * @return
	 */
	public V getValeur(C clef) {
		return this.clefVersValeur.get(clef);
	}
	
	/**
	 * Retourne la clef associée à une valeur donnée
	 * 
	 * @param valeur
	 * @return
	 */
	public C getClef(V valeur) {
		return this.valeurVersClef.get(valeur);
	}
	
	/**
	 * Supprime le couple (clef, valeur) et (valeur, clef)
	 * 
	 * @param valeur
	 */
	public void removeViaValeur(V valeur) {
		C clef = this.valeurVersClef.get(valeur);
		this.valeurVersClef.remove(valeur);
		this.clefVersValeur.remove(clef);
	}
	
	/**
	 * Supprime le couple (clef, valeur) et (valeur, clef)
	 * 
	 * @param clef
	 */
	public void removeViaClef(C clef) {
		V valeur = this.clefVersValeur.get(clef);
		this.clefVersValeur.remove(clef);
		this.valeurVersClef.remove(valeur);
	}
}
