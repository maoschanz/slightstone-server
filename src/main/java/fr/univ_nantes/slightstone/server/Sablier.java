package fr.univ_nantes.slightstone.server;

import java.util.Observable;

public class Sablier extends Observable {

	private int delai; // délai en seconde
	private Thread compteur;
	
	public Sablier(int delai) {
		this.delai = delai * 1000;
	}
	
	/**
	 * Averti la partie que le temps du joueur est dépassé
	 */
	private synchronized void alerter() {
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Lance le compte à rebours
	 */
	public void retourner() {
		this.compteur = new Thread(() -> {
	        try {
	            Thread.sleep(this.delai);
	            this.alerter();
	        }
	        catch (Exception e){
	            e.printStackTrace();
	        }
	    });
		this.compteur.start();
	}
	
	/**
	 * Arrête le compte à rebours
	 */
	public void arreter() {
		if(this.compteur != null) {
			this.compteur.interrupt();
		}
	}
}
