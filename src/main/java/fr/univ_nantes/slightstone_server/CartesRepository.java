package fr.univ_nantes.slightstone_server;

import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;

public interface CartesRepository extends CrudRepository<Carte, Integer> {
	List<Carte> findByClasseIn(Collection<String> collection);
}
