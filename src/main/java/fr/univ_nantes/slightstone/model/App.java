package fr.univ_nantes.slightstone.model;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

@ComponentScan({"fr.univ_nantes.slightstone.model","fr.univ_nantes.slightstone.server"})
@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
		app.run(args);
	}
	/*
	@Bean
	public CommandLineRunner initialisationBDD(HerosRepository hRepository, ActionsRepository aRepository, CartesRepository cRepository) {
		return (args) -> {
			//Action spéciale du Mage
			DescripteurSort actionSpecialeMage = new DescripteurSort("Boule de feu", "Boule de feu, infligeant un point de dégats à un adversaire (serviteur ou héros)", "bouleDeFeu.png", ClasseHeros.MAGE, 2);
			ActionAttaque bouleDeFeu = new ActionAttaque(TypeCible.UN_ADVERSAIRE, 1);
			actionSpecialeMage.ajouterAction(bouleDeFeu);
			aRepository.save(bouleDeFeu);
			cRepository.save(actionSpecialeMage);
			
			//Mage
			DescripteurHeros mage = new DescripteurHeros(ClasseHeros.MAGE, "mage.png", actionSpecialeMage);
			hRepository.save(mage);
			
			//Action spéciale du Paladin
			DescripteurSort actionSpecialePaladin = new DescripteurSort("Renfort", "Renfort, invoquant un serviteur \"recrue de la Main d'argent\" 1/1", "recrueMainArgent.png", ClasseHeros.PALADIN, 2);
			DescripteurServiteur serviteurRenfort = new DescripteurServiteur("Recrue de la main d'argent", "Serviteur 1/1", "recrueMainArgent.png", ClasseHeros.PALADIN, 0, 1, 1, false, false, false, false);
			ActionInvocation invocationRenfort = new ActionInvocation(serviteurRenfort);
			actionSpecialePaladin.ajouterAction(invocationRenfort);
			cRepository.save(serviteurRenfort);
			aRepository.save(invocationRenfort);
			cRepository.save(actionSpecialePaladin);
			
			//Paladin
			DescripteurHeros paladin = new DescripteurHeros(ClasseHeros.PALADIN, "paladin.png", actionSpecialePaladin);
			hRepository.save(paladin);
			
			//Action spéciale du Guerrier
			DescripteurSort actionSpecialeGuerrier = new DescripteurSort("Armure", "Armure, lui conférant 2 points d'armure", "armure.png", ClasseHeros.GUERRIER, 2);
			ActionBoostArmure armure = new ActionBoostArmure(2);
			actionSpecialeGuerrier.ajouterAction(armure);
			aRepository.save(armure);
			cRepository.save(actionSpecialeGuerrier);
			
			//Guerrier
			DescripteurHeros guerrier = new DescripteurHeros(ClasseHeros.GUERRIER, "guerrier.png", actionSpecialeGuerrier);
			hRepository.save(guerrier);
			
			//Cartes communes
			DescripteurServiteur sanglierBrocheroc = new DescripteurServiteur("Sanglier brocheroc", "Serviteur 1/1", "sanglierBrocheroc.png", ClasseHeros.COMMUN, 1, 1, 1, false, false, false, false);
			cRepository.save(sanglierBrocheroc);
			
			DescripteurServiteur soldatCompteOr = new DescripteurServiteur("Soldat du compté-de-l'or", "Serviteur 1/2", "soldatCompteOr.png", ClasseHeros.COMMUN, 1, 1, 2, true, false, false, false);
			cRepository.save(soldatCompteOr);
			
			DescripteurServiteur chevaucheurDeLoup = new DescripteurServiteur("Chevaucheur de loup", "Serviteur 3/1", "chevaucheurDeLoup.png", ClasseHeros.COMMUN, 3, 3, 1, false, true, false, false);
			cRepository.save(chevaucheurDeLoup);
			
			DescripteurServiteur chefDeRaid = new DescripteurServiteur("Chef de raid", "Serviteur 2/2", "chefDeRaid.png", ClasseHeros.COMMUN, 3, 2, 2, false, false, false, true);
			cRepository.save(chefDeRaid);
			
			DescripteurServiteur yetiNoroit = new DescripteurServiteur("Yéti noroit", "Serviteur 4/5", "yetiNoroit.png", ClasseHeros.COMMUN, 4, 4, 5, false, false, false, false);
			cRepository.save(yetiNoroit);
			
			//Cartes spécifiques au mage
			DescripteurSort imageMiroir = new DescripteurSort("Image miroir", "Sort de 1 point de mana, invoque deux serviteurs 0/2 avec provocation", "imageMiroir.png", ClasseHeros.MAGE, 1);
			DescripteurServiteur serviteurImageMiroir = new DescripteurServiteur("Serviteur miroir", "Serviteur 0/2", "serviteurMiroir.png", ClasseHeros.MAGE, 0, 0, 2, true, false, false, false);
			ActionInvocation invocationImageMiroir = new ActionInvocation(serviteurImageMiroir);
			imageMiroir.ajouterAction(invocationImageMiroir);
			imageMiroir.ajouterAction(invocationImageMiroir);
			cRepository.save(serviteurImageMiroir);
			aRepository.save(invocationImageMiroir);
			cRepository.save(imageMiroir);
			
			DescripteurSort explosionDesArcanes = new DescripteurSort("Explosion des arcanes", "Sort de 2 points de mana, inflige 1 point de dégats à tous les serviteurs adverses", "explosionDesArcanes.png", ClasseHeros.MAGE, 2);
			ActionAttaque attaqueExplosionDesArcanes = new ActionAttaque(TypeCible.TOUS_SERVITEURS_ADVERSES, 1);
			explosionDesArcanes.ajouterAction(attaqueExplosionDesArcanes);
			aRepository.save(attaqueExplosionDesArcanes);
			cRepository.save(explosionDesArcanes);
			
			DescripteurSort metamorphose = new DescripteurSort("Métamorphose", "Sort de 4 points de mana, transforme un serviteur en serviteur 1/1 sans effet spécial", "metamorphose.png", ClasseHeros.MAGE, 4);
			ActionMouton metamorphoseMouton = new ActionMouton();
			metamorphose.ajouterAction(metamorphoseMouton);
			aRepository.save(metamorphoseMouton);
			cRepository.save(metamorphose);
			
			//Cartes spécifiques au paladin
			DescripteurServiteur championFrisselame = new DescripteurServiteur("Champion frisselame", "Serviteur 3/2", "championFirsselame.png", ClasseHeros.PALADIN, 4, 3, 2, false, true, true, false);
			cRepository.save(championFrisselame);
			
			DescripteurSort benedictionDePuissance = new DescripteurSort("Bénédiction de puissance", "Sort de 1 point de mana, confère +3 d'attaque à un serviteur", "benedictionDePuissance.png", ClasseHeros.PALADIN, 1);
			ActionBoostDegats buffBenedictionDePuissance = new ActionBoostDegats(3);
			benedictionDePuissance.ajouterAction(buffBenedictionDePuissance);
			aRepository.save(buffBenedictionDePuissance);
			cRepository.save(benedictionDePuissance);
			
			DescripteurSort consecration = new DescripteurSort("Consécration", "Sort de 4 points de mana, inflige 2 points de dégâts à tous les adversaires", "consecration.png", ClasseHeros.PALADIN, 4);
			ActionAttaque attaqueConsecration = new ActionAttaque(TypeCible.TOUS_ADVERSAIRES, 2);
			consecration.ajouterAction(attaqueConsecration);
			aRepository.save(attaqueConsecration);
			cRepository.save(consecration);
			
			//Cartes spécifiques au guerrier
			DescripteurSort tourbillon = new DescripteurSort("Tourbillon", "Sort de 1 point de mana, inflige 1 point de dégats à tous les serviteurs (y compris les vôtres)", "tourbillon.png", ClasseHeros.GUERRIER, 1);
			ActionAttaque attaqueTourbillon = new ActionAttaque(TypeCible.TOUS_SERVITEURS, 1);
			tourbillon.ajouterAction(attaqueTourbillon);
			aRepository.save(attaqueTourbillon);
			cRepository.save(tourbillon);
			
			DescripteurServiteur avocatCommisOffice = new DescripteurServiteur("Avocat commis d'office", "Serviteur 0/7", "avocatCommisOffice.png", ClasseHeros.GUERRIER, 2, 0, 7, true, false, false, false);
			cRepository.save(avocatCommisOffice);
			
			DescripteurSort maitriseDuBlocage = new DescripteurSort("Maitrise du blocage", "Sort de 3 points de mana, +5 d'armure et place une carte aléatoire de la pioche dans votre main", "maitriseDuBlocage.png", ClasseHeros.GUERRIER, 3);
			ActionBoostArmure buffArmureMaitriseDuBlocage = new ActionBoostArmure(5);
			ActionPioche piocheMaitriseDuBlocage = new ActionPioche();
			maitriseDuBlocage.ajouterAction(buffArmureMaitriseDuBlocage);
			maitriseDuBlocage.ajouterAction(piocheMaitriseDuBlocage);
			aRepository.save(buffArmureMaitriseDuBlocage);
			aRepository.save(piocheMaitriseDuBlocage);
			cRepository.save(maitriseDuBlocage);
		};
	}
	*/
	/*
	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			ServiceJpaSlightstone test = ServiceJpaSlightstone.getService();
			test.resetDonnees();
			Jeu jeu = mock(Jeu.class);
			//Action spéciale du Mage
			DescripteurSort actionSpecialeMage = new DescripteurSort("Boule de feu", "Boule de feu, infligeant un point de dégats à un adversaire (serviteur ou héros)", "bouleDeFeu.png", ClasseHeros.MAGE, 2);
			ActionAttaque bouleDeFeu = new ActionAttaque(jeu, TypeCible.UN_ADVERSAIRE, 1);
			actionSpecialeMage.ajouterAction(bouleDeFeu);
			test.sauvegarderAction(bouleDeFeu);
			test.sauvegarderCarte(actionSpecialeMage);
			//Mage
			DescripteurHeros mage = new DescripteurHeros(ClasseHeros.MAGE, "mage.png", actionSpecialeMage);
			test.sauvegarderHeros(mage);
			
			System.out.println(test.getHeros(ClasseHeros.MAGE).toString());
		};
	}
	*/
}