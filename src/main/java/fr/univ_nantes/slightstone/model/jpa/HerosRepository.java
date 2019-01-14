package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.DescripteurHeros;

public interface HerosRepository extends CrudRepository<DescripteurHeros, Long> {
	DescripteurHeros findByClasse(ClasseHeros classe);
	
	@Query(
		value = "DELETE FROM heros",
		nativeQuery = true)
	@Modifying
	@Transactional
	public void supprimerHeros();
}
