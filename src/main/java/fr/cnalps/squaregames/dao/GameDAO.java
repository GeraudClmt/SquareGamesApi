package fr.cnalps.squaregames.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.BoardTokenModel;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.model.RemovedTokenModel;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Repository
public class GameDAO implements GameDAOInterface {

    private final Map<UUID, GameModel> games;

    public GameDAO() {
        games = new HashMap<>();
    }

    @Override
    public void deleteById(UUID gameId) throws IllegalArgumentException {
        GameModel game = games.remove(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return;
    }


    @Override
    public GameModel getGameModel(UUID gameId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameModel'");
    }

    @Override
    public List<UUID> getPlayers(UUID gameUuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlayers'");
    }

    @Override
    public List<TokenPosition<UUID>> getBoardTokens(List<UUID> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoardTokens'");
    }

    @Override
    public List<TokenPosition<UUID>> getRemovedTokens(List<UUID> players) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRemovedTokens'");
    }

    @Override
    public void savePlayerModel(PlayerModel playerModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePlayer'");
    }

    @Override
    public void saveGame(Game game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGame'");
    }

    @Override
    public void saveGameModel(GameModel gameModel) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveGameModel'");
    }

    @Override
    public void saveBoardTokens(BoardTokenModel boardTokenModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveRemovedTokens(RemovedTokenModel removedTokenModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
