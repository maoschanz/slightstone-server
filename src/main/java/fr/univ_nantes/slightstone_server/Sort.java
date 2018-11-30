package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Sort extends Carte {
	protected Sort () {}
	
	public Sort (String nom, Integer coutMana, String classe, boolean piochable) {
		super(nom, coutMana, classe, piochable);
		
	}
}
