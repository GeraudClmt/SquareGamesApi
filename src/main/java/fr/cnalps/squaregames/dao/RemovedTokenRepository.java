package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.RemovedTokenModel;

public interface RemovedTokenRepository extends CrudRepository<RemovedTokenModel, Integer>{
    List<RemovedTokenModel> findByPlayerUuid(UUID uuid);
    
}
