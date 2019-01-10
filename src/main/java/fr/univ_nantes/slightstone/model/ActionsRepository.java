package fr.univ_nantes.slightstone.model;

import org.springframework.data.repository.CrudRepository;

import fr.univ_nantes.slightstone.model.actions.Action;

public interface ActionsRepository extends CrudRepository<Action, Long> {}
