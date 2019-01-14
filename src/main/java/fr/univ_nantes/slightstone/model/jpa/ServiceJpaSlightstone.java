package fr.univ_nantes.slightstone.model.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import fr.univ_nantes.slightstone.model.Action;
import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.DescripteurCarte;
import fr.univ_nantes.slightstone.model.DescripteurHeros;

@Service
public class ServiceJpaSlightstone {
	
	/* ******************************* */
	/* ********** Attributs ********** */
	/* ******************************* */
	
	@Autowired
	private HerosRepository hRepository;
	@Autowired
	private CartesRepository cRepository;
	@Autowired
	private ActionsRepository aRepository;
		
	private static ServiceJpaSlightstone service = null;
	
	@Bean
	public static ServiceJpaSlightstone getService() {
		if(service == null) {
			ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
			service = context.getBean(ServiceJpaSlightstone.class);
			((AnnotationConfigApplicationContext) context).close();
		}
		return service;
	}
	
	public void resetDonnees() {
		this.aRepository.supprimerActionsAttaque();
		this.aRepository.supprimerActionsBoostArmure();
		this.aRepository.supprimerActionsBoostDegats();
		this.aRepository.supprimerActionsInvocation();
		this.aRepository.supprimerActionsMouton();
		this.aRepository.supprimerActionsPioche();
		this.cRepository.supprimerActionsSorts();
		this.aRepository.supprimerActions();
		this.hRepository.supprimerHeros();
		this.cRepository.supprimerSorts();
		this.cRepository.supprimerServiteurs();
		this.cRepository.supprimerCartes();
	}
	
	public void sauvegarderAction(Action action) {
		this.aRepository.save(action);
	}
	
	public void sauvegarderCarte(DescripteurCarte carte) {
		this.cRepository.save(carte);
	}
	
	public void sauvegarderHeros(DescripteurHeros heros) {
		this.hRepository.save(heros);
	}
	
	public DescripteurHeros getHeros(ClasseHeros classe) {
		return this.hRepository.findByClasse(classe);
	}
	
	public List<DescripteurCarte> construireDeck(ClasseHeros classeHeros) {
		return cRepository.findByClasseOrClasseAndPiochable(ClasseHeros.COMMUN, classeHeros, true);
	}
}
