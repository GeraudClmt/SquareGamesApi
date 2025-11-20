package fr.cnalps.squaregames.dao;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;

public interface GameDAOInterface {

    Game getGameById(UUID gameId);
    void save(Game game);
    void deleteById(UUID gameId) throws IllegalArgumentException;
    Map<UUID, Game>  findAll();
    Map<CellPosition, Token> getBoardByGameId(UUID gameId) throws IllegalArgumentException;
    Collection<Token> getRemainingToken(UUID gameId)throws IllegalArgumentException;
    void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
    void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
}
