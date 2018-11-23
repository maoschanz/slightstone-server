package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;

@Entity
public class Action {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	protected Action () {}
	
//	public Action () {
		
/*
1. Sorts d'invocation
- Image miroir

2. Modification de serviteur
- Métamorphose
- Bénédiction de puissance

3. Attaque de masse
- Explosion des arcanes
- Consécration
- Tourbillon

4. "Maîtrise du blocage" (!)

*/
//	}
}
