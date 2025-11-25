package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.cnalps.squaregames.model.PlayerModel;

public interface PlayerRepository extends CrudRepository<PlayerModel, Integer> {

    @Query("SELECT DISTINCT p.uuid FROM PlayerModel p WHERE p.game.uuid = :gameUuid")
    List<UUID> findPlayerUuidsByGameUuid(@Param("gameUuid") UUID gameUuid);

    PlayerModel findByUuid(UUID uuid);

}
