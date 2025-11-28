package fr.cnalps.squaregames.service;

import java.util.UUID;

import org.springframework.dao.DataAccessException;

import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;

public interface GameServiceInterface {
    UUID create(GameCreationParamsRequest gameCreationParamsRequest, String namePlayer1, String namePlayer2);
    boolean moveToken(UUID gameId,  GameMoveTokenParamsRequest gameMoveTokenParamsRequest) throws IllegalArgumentException, InvalidPositionException;
    void deleteGame(UUID gameId);
    Game getGameByid(UUID gameId)throws DataAccessException, InconsistentGameDefinitionException;
}
