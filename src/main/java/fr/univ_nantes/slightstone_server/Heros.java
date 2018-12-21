package fr.univ_nantes.slightstone_server;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Heros {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer pointsVie;
	private Integer pointsArmure;
	private String imageURL;
	private TypeHeros classe;

	//private Integer idTypeCarte; //??

	@Transient //TODO joinoncolumn
	private DescripteurSort actionSpe;

	protected Heros () {}

	public Heros (Integer pv, Integer pa, Integer idTypeCarte) {
		// TODO instancier une action spéciale depuis le JPA
		this.pointsVie = pv;
		this.pointsArmure = pa;
	}

	public Integer getPointsVie() {
		return this.pointsVie;
	}

	public Integer getPointsArmure() {
		return this.pointsArmure;
	}

	public void jouerActionSpeciale () {
		this.actionSpe.lancerActions();
	}
}