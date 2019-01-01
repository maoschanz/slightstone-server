package fr.univ_nantes.slightstone.model;

import org.springframework.data.repository.CrudRepository;

public interface HerosRepository extends CrudRepository<DescripteurHeros, Long> {
	DescripteurHeros findByClasse(ClasseHeros classe);
}
