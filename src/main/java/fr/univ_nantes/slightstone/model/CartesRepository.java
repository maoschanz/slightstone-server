package fr.univ_nantes.slightstone.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartesRepository extends CrudRepository<DescripteurCarte, Long> {
	List<DescripteurCarte> findByClasseOrClasse(ClasseHeros classe1, ClasseHeros classe2);
}