package fr.univ_nantes.slightstone_server;

import java.util.Collection;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}

	/*@Bean
	public CommandLineRunner demoCartes(CartesRepository repository) {
		return (args) -> {
			// save a couple of customers
			
			// DescripteurCarte carte1 = new DescripteurCarte("Sanglier brocheroc", "carte1", "url", 1, TypeHeros.COMMUN);
			DescripteurCarte sort1 = new DescripteurSort("Boule de feu", "sort1", "url", 2, TypeHeros.PALADIN);
			DescripteurCarte serviteur1 = new DescripteurServiteur("Nestor", "serviteur1", "url", 3, TypeHeros.COMMUN, 50, 20, true, true, true, true);
			
			// repository.save(carte1);
			repository.save(sort1);
			repository.save(serviteur1);
			
			// fetch all cartes
			System.out.println("Cartes found with findAll():");
			System.out.println("-------------------------------");
			for (DescripteurCarte customer : repository.findAll()) {
				System.out.println(customer.toString());
			}
			System.out.println("");

			// fetch an individual carte by ID
			repository.findById(1)
				.ifPresent(customer -> {
					System.out.println("Carte found with findById(1):");
					System.out.println("--------------------------------");
					System.out.println(customer.toString());
					System.out.println("");
				});

			System.out.println("Cartes found with findByClasseOrClasse('commun', 'mage'):");
			System.out.println("--------------------------------------------");
			repository.findByClasseOrClasse("mage", "commun").forEach(instance -> {
				System.out.println(instance.toString());
			});
			System.out.println("");
		};
	}*/
	
	@Bean
	public CommandLineRunner demoAction(ActionsRepository aRepository, CartesRepository cRepository) {
		return (args) -> {

			Joueur j1 = new Joueur("pascal", TypeHeros.PALADIN, true);
			Joueur j2 = new Joueur("jean-xavier", TypeHeros.GUERRIER, false);
			Jeu jeu = new Jeu(j1, j2);
			
			DescripteurServiteur serviteur = new DescripteurServiteur("Nestor", "serviteur1", "url", 3, TypeHeros.COMMUN, 50, 20, true, true, true, true);
			cRepository.save(serviteur);	
			
			ActionBoostArmure boost = new ActionBoostArmure(jeu, TypeCible.HEROS_ALLIE, 2);
			ActionModifVieIndiv degatHP = new ActionModifVieIndiv(jeu, TypeCible.UN_SERVITEUR_ADVERSE, -3);
			ActionInvocation invocation = new ActionInvocation(jeu, TypeCible.AUCUN, serviteur);
			
			aRepository.save(boost);
			aRepository.save(degatHP);
			aRepository.save(invocation);
			
			DescripteurSort sort = new DescripteurSort("Boule de feu", "sort1", "url", 2, TypeHeros.PALADIN);
			sort.ajouterAction(boost);
			sort.ajouterAction(degatHP);
			sort.ajouterAction(invocation);
			cRepository.save(sort);
			
			// fetch all cartes
			System.out.println("Actions found with findAll():");
			System.out.println("-----------------------------");
			for (Action action : aRepository.findAll()) {
				System.out.println(action.toString());
				action.executer();
			}
			System.out.println("");
			
			cRepository.findByClasseOrClasse(TypeHeros.COMMUN, TypeHeros.PALADIN).forEach(instance -> {
				if(instance instanceof DescripteurSort) {
					((DescripteurSort) instance).lancerActions();
				}
			});
		};
	}
	
	@Bean
	public CommandLineRunner demoHeros(HerosRepository hRepository, ActionsRepository aRepository, CartesRepository cRepository) {
		return (args) -> {

			Joueur j1 = new Joueur("pascal", TypeHeros.PALADIN, true);
			Joueur j2 = new Joueur("jean-xavier", TypeHeros.GUERRIER, false);
			Jeu jeu = new Jeu(j1, j2);
			
			ActionBoostArmure boost = new ActionBoostArmure(jeu, TypeCible.HEROS_ALLIE, 2);
			ActionModifVieIndiv degatHP = new ActionModifVieIndiv(jeu, TypeCible.UN_SERVITEUR_ADVERSE, -3);
			
			aRepository.save(boost);
			aRepository.save(degatHP);
			
			DescripteurSort sort = new DescripteurSort("Boule de feu", "sort1", "url", 2, TypeHeros.PALADIN);
			sort.ajouterAction(boost);
			sort.ajouterAction(degatHP);
			cRepository.save(sort);
			
			Heros paladin = new Heros(TypeHeros.PALADIN, 30, 0, sort);
			hRepository.save(paladin);
			
			for (Heros heros : hRepository.findAll()) {
				System.out.println(heros.toString());
				heros.jouerActionSpeciale();
			}
		};
	}

	/*@Bean
	public CommandLineRunner demoHeros(HerosRepository repository) {
		return (args) -> {
			Heros heros1 = new Heros(3, 5, 1);
			Heros heros2 = new Heros(5, 7, 2);

			repository.save(heros1);
			repository.save(heros2);

			// fetch all cartes
			System.out.println("Heros found with findAll():");
			System.out.println("-------------------------------");
			for (Heros heros : repository.findAll()) {
				System.out.println(heros.toString());
			}
			System.out.println("");
		};
	}*/
}