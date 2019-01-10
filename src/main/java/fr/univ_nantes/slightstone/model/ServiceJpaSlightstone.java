package fr.univ_nantes.slightstone.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import fr.univ_nantes.slightstone.model.actions.Action;

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
		this.hRepository.deleteAll();
		this.cRepository.deleteAll();
		this.aRepository.deleteAll();
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
		return cRepository.findByClasseOrClasse(ClasseHeros.COMMUN, classeHeros);
	}
}
