package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.cnalps.squaregames.model.GameModel;



public interface GameRepository extends CrudRepository<GameModel, Integer> {

    GameModel findByUuid(UUID uuid);
    @Transactional
    void deleteByUuid(UUID uuid);
}
