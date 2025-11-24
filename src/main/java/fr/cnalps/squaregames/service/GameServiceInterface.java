package fr.cnalps.squaregames.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.dao.DataAccessException;

import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;

public interface GameServiceInterface {
    UUID create(GameCreationParamsRequest gameCreationParamsRequest);
    GameStatus getStatus(UUID gameId)throws IllegalArgumentException, InconsistentGameDefinitionException ;
    Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y)throws  DataAccessException, InconsistentGameDefinitionException ;
    Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName)throws  DataAccessException, InconsistentGameDefinitionException;
    boolean moveToken(UUID gameId,  GameMoveTokenParamsRequest gameMoveTokenParamsRequest) throws IllegalArgumentException, InvalidPositionException;
    void deleteGame(UUID gameId);
    Game getGameByid(UUID gameId)throws DataAccessException, InconsistentGameDefinitionException;
}
