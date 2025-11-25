package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.cnalps.squaregames.dao.GameDAOInterface;
import fr.cnalps.squaregames.dao.GameDAOJpa;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.plugin.GamePluginInterface;
import fr.cnalps.squaregames.request.GameCreationParamsRequest;
import fr.cnalps.squaregames.request.GameMoveTokenParamsRequest;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Service
public class GameServiceImpl implements GameServiceInterface {

    @Autowired
    List<GamePluginInterface> gamePluginInstefaceList;
    @Autowired
    GameDAOInterface gameDAO;


    @Override
    public UUID create(GameCreationParamsRequest gameCreationParamsRequest) throws IllegalArgumentException {
        String gameIdentifier = gameCreationParamsRequest.getGameName();

        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {
                Game game = gamePlugin.createGame(gameCreationParamsRequest.getPlayerCount(),
                        gameCreationParamsRequest.getBoardSize());
                Collection<Token> remainingTokens = game.getRemainingTokens();
                Map<CellPosition, Token> board = game.getBoard();

                GameModel gameModel = new GameModel();
                gameModel.setUuid(game.getId());
                gameModel.setBoard_size(game.getBoardSize());
                gameModel.setFactory_id(gameIdentifier);
                gameDAO.saveGame(gameModel);

                List<PlayerModel> playerModels = new ArrayList<>();
                Map<UUID, String> playerAndName = new HashMap<>();

                for (Token token : remainingTokens) {
                    UUID uuid = token.getOwnerId().orElse(null);
                    String name = token.getName();

                    if (uuid != null && playerAndName.get(uuid) == null) {
                        playerAndName.put(uuid, name);
                    }
                }

                for (Token token : board.values()) {
                    UUID uuid = token.getOwnerId().orElse(null);
                    String name = token.getName();

                    if (uuid != null && playerAndName.get(uuid) == null) {
                        playerAndName.put(uuid, name);
                    }
                }

                for (Map.Entry<UUID, String> entry : playerAndName.entrySet()) {
                    UUID uuid = entry.getKey();
                    String name = entry.getValue();

                    PlayerModel playerModel = new PlayerModel();
                    playerModel.setUuid(uuid);
                    playerModel.setGame(gameModel);
                    playerModel.setToken_name(name);

                    playerModels.add(playerModel);
                }

                for (PlayerModel playerModel : playerModels) {
                    if (playerModel != null) {
                        gameDAO.savePlayer(playerModel);
                    }
                }

                return game.getId();
            }
        }

        throw new IllegalArgumentException("Game name not found");

    }

    @Override
    public Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y)
            throws IllegalArgumentException, DataAccessException, InconsistentGameDefinitionException {
        CellPosition cellPositions = new CellPosition(x, y);

        Map<CellPosition, Token> currentBoard = getGameByid(gameId).getBoard();

        for (CellPosition positions : currentBoard.keySet()) {
            if (positions.x() == cellPositions.x() && positions.y() == cellPositions.y()) {
                return currentBoard.get(positions).getAllowedMoves();
            }
        }
        throw new IllegalArgumentException("Token name not found");

    }

    @Override
    public Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName)
            throws IllegalArgumentException, DataAccessException, InconsistentGameDefinitionException {
        Collection<Token> remainingTokens = getGameByid(gameId).getRemainingTokens();

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
    public Integer deleteGame(UUID gameId) {
        return gameDAO.deleteById(gameId);
    }

    @Override
    public Game getGameByid(UUID gameId) throws DataAccessException, InconsistentGameDefinitionException {
        GameModel gameModel = gameDAO.getGameModel(gameId);
        String gameIdentifier = gameModel.getFactory_id();
        int boardSize = gameModel.getBoard_size();

        List<UUID> players = gameDAO.getPlayers(gameId);

        List<TokenPosition<UUID>> boardTokens = gameDAO.getBoardTokens(players);

        // for (TokenPosition<UUID> tokenPosition : boardTokens) {
        // System.out.println("Board Token - User UUID: " + tokenPosition.owner() + ",
        // Token Name: "
        // + tokenPosition.tokenName() + ", Position: (" + tokenPosition.x() + ", " +
        // tokenPosition.y() + ")");
        // }

        List<TokenPosition<UUID>> removedTokens = gameDAO.getRemovedTokens(players);

        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {

                Game game = gamePlugin.createGameWithIds(gameId,
                        boardSize,
                        players,
                        boardTokens,
                        removedTokens);

                //System.out.println(game.getBoard());
                return game;
            }
        }
        throw new IllegalArgumentException("Game name not found");
    }



}