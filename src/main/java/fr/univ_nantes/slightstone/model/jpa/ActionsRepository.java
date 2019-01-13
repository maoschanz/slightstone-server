package fr.univ_nantes.slightstone.model.jpa;

import org.springframework.data.repository.CrudRepository;

import fr.univ_nantes.slightstone.model.Action;

public interface ActionsRepository extends CrudRepository<Action, Long> {}
