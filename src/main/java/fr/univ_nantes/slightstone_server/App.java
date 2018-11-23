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

	@Bean
	public CommandLineRunner demo(CartesRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Carte("Sanglier brocheroc", "1/1/1"));
			repository.save(new Carte("Yéti noroit", "4/4/5"));
			repository.save(new Carte("Métamorphoese", "sort...."));

			// fetch all cartes
			System.out.println("Cartes found with findAll():");
			System.out.println("-------------------------------");
			for (Carte customer : repository.findAll()) {
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

			// fetch cartes by last name
			System.out.println("Cartes found with findByClasseIn('commun', 'mage'):");
			System.out.println("--------------------------------------------");
			Collection<String> collection = new ArrayList();
			collection.add("commun");
			collection.add("mage");
			repository.findByClasseIn(collection).forEach(bauer -> {
				System.out.println(bauer.toString());
			});
			System.out.println("");
		};
	}
}
