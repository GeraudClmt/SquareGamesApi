package fr.cnalps.squaregames.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Repository
public class GameDAO implements GameDAOInterface {

    private final Map<UUID, GameModel> games;

    public GameDAO() {
        games = new HashMap<>();
    }

    @Override
    public Integer deleteById(UUID gameId) throws IllegalArgumentException {
        GameModel game = games.remove(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return 1;
    }


    @Override
    public void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition)
            throws InvalidPositionException {
        // Map<CellPosition, Token> currentBoard = getBoardByGameId(gameId);
        // Token curentToken = currentBoard.get(startPosition);
        // curentToken.moveTo(endPosition);
        throw new IllegalArgumentException("Token name not found");
    }

    @Override
    public void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException {
        // Game game = getGameById(gameId);
        // Collection<Token> remainingTokens = game.getRemainingTokens();

        // for (Token token : remainingTokens) {
        //     if (token.getName().equals(tokenName)) {
        //         token.moveTo(endPosition);
        //         return;
        //     }
        // }
        throw new IllegalArgumentException("Token name not found");
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

}
