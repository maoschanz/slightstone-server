package fr.univ_nantes.slightstone.server;

import java.security.Principal;

import fr.univ_nantes.slightstone.server.messages.MessageAttaquer;
import fr.univ_nantes.slightstone.server.messages.MessageJouerCarteServiteur;
import fr.univ_nantes.slightstone.server.messages.MessageJouerCarteSort;
import fr.univ_nantes.slightstone.server.messages.MessageLancerActionSpecial;
import fr.univ_nantes.slightstone.server.messages.MessageNouvellePartie;

public interface IServeur{
	void nouvellePartie(Principal principal, MessageNouvellePartie message);
	void jouerCarteServiteur(Principal principal, MessageJouerCarteServiteur message);
	void jouerCarteSort(Principal principal, MessageJouerCarteSort message);
	void terminerTour(Principal principal);
	void lancerActionHeros(Principal principal, MessageLancerActionSpecial message);
	void attaquer(Principal principal, MessageAttaquer message);
}
