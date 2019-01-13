package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.repository.CrudRepository;

import fr.univ_nantes.slightstone.model.ClasseHeros;
import fr.univ_nantes.slightstone.model.DescripteurCarte;

import java.util.List;

public interface CartesRepository extends CrudRepository<DescripteurCarte, Long> {
	List<DescripteurCarte> findByClasseOrClasseAndPiochable(ClasseHeros classe1, ClasseHeros classe2, boolean piochable);
}