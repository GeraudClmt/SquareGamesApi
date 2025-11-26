package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.BoardTokenModel;

public interface BoardTokenRepository extends CrudRepository<BoardTokenModel, Integer> {

    List<BoardTokenModel> findByPlayerUuid(UUID uuid);
    
}
