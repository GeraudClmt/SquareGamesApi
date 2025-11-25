package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import fr.cnalps.squaregames.model.Game;
import fr.cnalps.squaregames.model.Player;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

public interface GameDAOInterface {

    public int saveGame(Game gameModel) throws IllegalArgumentException;
    public int savePlayer(Player playerModel);

    public Game getGameModel(UUID gameId);
    public List<UUID> getPlayers(UUID gameUuid);
    public List<TokenPosition<UUID>> getBoardTokens(List<UUID> players);
    public List<TokenPosition<UUID>> getRemovedTokens(List<UUID> players);

    void deleteById(UUID gameId) throws IllegalArgumentException;
    void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
    void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
}
