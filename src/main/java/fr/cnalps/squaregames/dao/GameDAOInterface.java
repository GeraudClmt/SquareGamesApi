package fr.cnalps.squaregames.dao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import fr.cnalps.squaregames.model.BoardTokenModel;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.model.RemovedTokenModel;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

public interface GameDAOInterface {
    public void saveGame(Game game);
    public void saveGameModel(GameModel gameModel) throws IllegalArgumentException;
    public void savePlayerModel(PlayerModel playerModel);
    public void saveBoardTokens(BoardTokenModel boardTokenModel);
    public void saveRemovedTokens(RemovedTokenModel removedTokenModel);

    public GameModel getGameModel(UUID gameId);
    public List<UUID> getPlayers(UUID gameUuid);
    public Collection<TokenPosition<UUID>> getBoardTokens(UUID gameUuid);
    public Collection<TokenPosition<UUID>> getRemovedTokens(UUID gameUuid);

    void deleteById(UUID gameId) throws IllegalArgumentException;
}