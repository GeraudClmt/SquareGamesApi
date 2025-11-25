package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.GameModel;



public interface GameRepository extends CrudRepository<GameModel, Integer> {

    List<GameModel> findByUuid(UUID uuid);
    Integer deleteByUuid(UUID uuid);
}
