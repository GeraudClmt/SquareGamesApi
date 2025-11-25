package fr.cnalps.squaregames.dao;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {

}
