package fr.cnalps.squaregames.dao;

import java.util.List;
import java.util.UUID;

import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

public interface GameDAOInterface {
    public void saveGame(Game game);
    public void saveGameModel(GameModel gameModel) throws IllegalArgumentException;
    public void savePlayerModel(PlayerModel playerModel);

    public GameModel getGameModel(UUID gameId);
    public List<UUID> getPlayers(UUID gameUuid);
    public List<TokenPosition<UUID>> getBoardTokens(List<UUID> players);
    public List<TokenPosition<UUID>> getRemovedTokens(List<UUID> players);

    Integer deleteById(UUID gameId) throws IllegalArgumentException;
    void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
    void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException;
}
