package fr.univ_nantes.slightstone_server;

import org.springframework.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;

public interface ActionsRepository extends CrudRepository<Action, Integer> {}