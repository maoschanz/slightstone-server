package fr.univ_nantes.slightstone_server;

import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;

public interface CartesRepository extends CrudRepository<DescripteurCarte, Integer> {
	List<DescripteurCarte> findByClasseIn(Collection<String> collection);
	List<DescripteurCarte> findByClasseOrClasse(String classe1, String classe2);
} //XXX faire 2 repo pour sorts vs. serviteurs ???