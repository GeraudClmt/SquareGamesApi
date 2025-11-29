package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.cnalps.squaregames.model.RemovedTokenModel;

public interface RemovedTokenRepository extends CrudRepository<RemovedTokenModel, Integer>{
    List<RemovedTokenModel> findByPlayerUuid(UUID uuid);
    
    @Query("SELECT r FROM RemovedTokenModel r WHERE r.game.uuid = :gameUuid")
    List<RemovedTokenModel> findByGameUuid(@Param("gameUuid") UUID uuid);
}
