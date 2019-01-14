package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_nantes.slightstone.model.Action;

public interface ActionsRepository extends CrudRepository<Action, Long> {
	
	@Query(
			value = "DELETE FROM action_invocation",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsInvocation();
	
	@Query(
			value = "DELETE FROM action_mouton",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsMouton();
	
	@Query(
			value = "DELETE FROM action_pioche",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsPioche();
	
	@Query(
			value = "DELETE FROM action_attaque",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsAttaque();
	
	@Query(
			value = "DELETE FROM action_boost_armure",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsBoostArmure();
	
	@Query(
			value = "DELETE FROM action_boost_degats",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsBoostDegats();
	
	@Query(
			value = "DELETE FROM actions",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActions();
}