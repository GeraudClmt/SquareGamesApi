package fr.cnalps.squaregames.service;

import java.util.Set;
import java.util.UUID;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;

public interface GameServiceInterface {
    UUID create(String gameId, int playerCount, int boardSize);
    GameStatus getStatus(UUID gameId);
    Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y);
    Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName);
    boolean moveToken(UUID tokenId, CellPosition startPositionn, CellPosition endPosition);
    Game getGame(UUID gameId);
}
