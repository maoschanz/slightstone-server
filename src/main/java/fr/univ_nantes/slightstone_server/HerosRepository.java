package fr.univ_nantes.slightstone_server;

import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;

public interface HerosRepository extends CrudRepository<Heros, Integer> {}
