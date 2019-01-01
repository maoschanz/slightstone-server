package fr.univ_nantes.slightstone.model;

public enum TypeCible {
	AUCUNE, // l'action ne nécessite aucune cible
	TOUS_SERVITEURS, // l'action cible tous les serviteurs (allié et adverse)
	UN_SERVITEUR_ALLIE, // l'action cible l'un de nos serviteurs
	UN_SERVITEUR_ADVERSE, // l'action cible l'un des serviteurs de l'adversaire
	TOUS_SERVITEURS_ADVERSES, // l'action cible tous les serviteurs de l'adversaire
	TOUS_ADVERSAIRES, // l'action cible tous les adversaires (héros et serviteurs)
	UN_ADVERSAIRE // l'action cible un adversaire (soit le héros, soit un serviteur)
}