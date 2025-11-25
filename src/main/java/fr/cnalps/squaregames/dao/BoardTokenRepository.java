package fr.cnalps.squaregames.dao;

import org.springframework.data.repository.CrudRepository;

import fr.cnalps.squaregames.model.BoardToken;

public interface BoardTokenRepository extends CrudRepository<BoardToken, Integer> {

}
