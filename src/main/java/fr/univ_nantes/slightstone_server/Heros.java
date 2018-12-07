package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Heros {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer pointsVie;
	private Integer pointsArmure;
	private Integer idActionSpeciale;
	private String imageURL;
	private TypeHeros classe;

	protected Heros () {} // TODO? ajouter de la persistence pour ça ?

	public Heros (Integer pv, Integer pa, Integer idActionSpeciale) {
		// TODO
		this.pointsVie = pv;
		this.pointsArmure = pa;
		this.idActionSpeciale = idActionSpeciale;

	}

	public Integer getPointsVie() {
		return this.pointsVie;
	}

	public Integer getPointsArmure() {
		return this.pointsArmure;
	}

	// public Carte getActionSpeciale() {
	// 	Carte c = pioche.getCarteParId(this.idActionSpeciale) // TODO
	// 	return c;
	// }
}