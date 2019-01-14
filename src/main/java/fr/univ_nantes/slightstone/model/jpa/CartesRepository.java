package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.DescripteurCarte;

import java.util.List;

public interface CartesRepository extends CrudRepository<DescripteurCarte, Long> {
	List<DescripteurCarte> findByClasseOrClasseAndPiochable(ClasseHeros classe1, ClasseHeros classe2, boolean piochable);
	
	@Query(
			value = "DELETE FROM serviteurs",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerServiteurs();
	
	@Query(
			value = "DELETE FROM sorts",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerSorts();
	
	@Query(
			value = "DELETE FROM sorts_actions",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerActionsSorts();
	
	@Query(
			value = "DELETE FROM cartes",
			nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerCartes();	
}