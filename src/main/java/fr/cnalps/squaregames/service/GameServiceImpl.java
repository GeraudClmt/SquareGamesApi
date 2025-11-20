package fr.cnalps.squaregames.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.cnalps.squaregames.dao.GameDAOInterface;
import fr.cnalps.squaregames.plugin.GamePluginInterface;
import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;

@Service
public class GameServiceImpl implements GameServiceInterface {

    private final List<GamePluginInterface> gamePluginInstefaceList;
    private final GameDAOInterface gameDAO;

    public GameServiceImpl(List<GamePluginInterface> gamePluginInstefaceList, GameDAOInterface gameDAO) {
        this.gamePluginInstefaceList = gamePluginInstefaceList;
        this.gameDAO = gameDAO;

    }

    @Override
    public UUID create(GameCreationParamsRequest gameCreationParamsRequest) throws IllegalArgumentException {
        String gameName = gameCreationParamsRequest.getGameName();

        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameName.equals(gamePlugin.getGamePluginId())) {
                Game game = gamePlugin.createGame(gameCreationParamsRequest.getPlayerCount(),
                        gameCreationParamsRequest.getBoardSize());
                gameDAO.save(game);
                return game.getId();
            }
        }

        throw new IllegalArgumentException("Game name not found");

    }

    @Override
    public GameStatus getStatus(UUID gameId) throws IllegalArgumentException {
        Game game = gameDAO.getGameById(gameId);
        return game.getStatus();
    }

    @Override
    public Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y)
            throws IllegalArgumentException {
        CellPosition cellPositions = new CellPosition(x, y);

        Map<CellPosition, Token> currentBoard = gameDAO.getBoardByGameId(gameId);

        for (CellPosition positions : currentBoard.keySet()) {
            if (positions.x() == cellPositions.x() && positions.y() == cellPositions.y()) {
                return currentBoard.get(positions).getAllowedMoves();
            }
        }
        throw new IllegalArgumentException("Token name not found");

    }

    @Override
    public Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName)
            throws IllegalArgumentException {
        Collection<Token> remainingTokens = gameDAO.getRemainingToken(gameId);

        for (Token token : remainingTokens) {
            if (token.getName().equals(tokenName)) {
                return token.getAllowedMoves();
            }
        }
        throw new IllegalArgumentException("Token name not found");

    }

    @Override
    public boolean moveToken(UUID gameId, GameMoveTokenParamsRequest gameMoveTokenParamsRequest)
            throws IllegalArgumentException, InvalidPositionException {
        CellPosition startPosition = gameMoveTokenParamsRequest.getStartPosition();
        CellPosition endPosition = gameMoveTokenParamsRequest.getEndPosition();
        String tokenName = gameMoveTokenParamsRequest.getTokenName();

        if (startPosition != null) {
            gameDAO.moveTokenWithStartPosition(gameId, startPosition, endPosition);
            return true;
        }

        gameDAO.moveTokenByRemaining(gameId, tokenName, endPosition);
        return true;
    }

    @Override
    public void deleteGame(UUID gameId) throws IllegalArgumentException {
        gameDAO.deleteById(gameId);
    }
}