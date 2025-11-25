package fr.cnalps.squaregames.dao;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

}
