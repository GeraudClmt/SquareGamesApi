package fr.cnalps.squaregames.service;

import java.util.Set;
import java.util.UUID;

import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;

public interface GameServiceInterface {
    UUID create(GameCreationParamsRequest gameCreationParamsRequest);
    GameStatus getStatus(UUID gameId);
    Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y);
    Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName);
    boolean moveToken(UUID gameId,  GameMoveTokenParamsRequest gameMoveTokenParamsRequest) throws IllegalArgumentException, InvalidPositionException;
    Game getGame(UUID gameId);
}
