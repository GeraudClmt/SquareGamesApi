package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.cnalps.squaregames.model.BoardTokenModel;

public interface BoardTokenRepository extends CrudRepository<BoardTokenModel, Integer> {

    List<BoardTokenModel> findByPlayerUuid(UUID uuid);
    
    @Query("SELECT b FROM BoardTokenModel b WHERE b.game.uuid = :gameUuid")
    List<BoardTokenModel> findByGameUuid(@Param("gameUuid") UUID uuid);
    
}
