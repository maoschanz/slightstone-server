package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.repository.CrudRepository;

import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.DescripteurHeros;

public interface HerosRepository extends CrudRepository<DescripteurHeros, Long> {
	DescripteurHeros findByClasse(ClasseHeros classe);
}
