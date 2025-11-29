package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.UnknownContentTypeException;

import fr.cnalps.squaregames.dao.GameDAOInterface;
import fr.cnalps.squaregames.dto.UserDto;
import fr.cnalps.squaregames.model.GameModel;
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

    private final String urlApiUser = "http://localhost:9090/users/";

    @Override
    public UUID create(GameCreationParamsRequest gameCreationParamsRequest, String namePlayer1, String namePlayer2)
            throws IllegalArgumentException {
        String gameIdentifier = gameCreationParamsRequest.getGameName();
        RestClient restClient = RestClient.builder().build();

        try {
            /*UserDto player1 = restClient.get()
                    .uri(urlApiUser + "/name/{namePlayer1}", namePlayer1)
                    .retrieve()
                    .body(UserDto.class);

            UserDto player2 = restClient.get()
                    .uri(urlApiUser + "/name/{namePlayer2}", namePlayer2)
                    .retrieve()
                    .body(UserDto.class);

            Set<UUID> players = Set.of(player1.getUuid(), player2.getUuid());*/
            Set<UUID> players = Set.of(UUID.randomUUID(), UUID.randomUUID());

            for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
                if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {
                    Game game = gamePlugin.createGame(gameCreationParamsRequest.getBoardSize(), players);

                    gameDAO.saveGame(game);

                    return game.getId();

                }
            }
            throw new IllegalArgumentException("Game name not found");
        } catch (UnknownContentTypeException e) {
            throw new IllegalArgumentException("Players not found");
        }
    }

    @Override
    public boolean moveToken(UUID gameId, GameMoveTokenParamsRequest gameMoveTokenParamsRequest)
            throws IllegalArgumentException, InvalidPositionException {

        try {
            Game game = getGameByid(gameId);

            CellPosition startPosition = gameMoveTokenParamsRequest.getStartPosition();
            CellPosition endPosition = gameMoveTokenParamsRequest.getEndPosition();
            String tokenName = gameMoveTokenParamsRequest.getTokenName();

            Collection<Token> tokens = new ArrayList<>();

            if (startPosition == null) {
                tokens = game.getRemainingTokens();
            } else {
                tokens = game.getBoard().values();
            }

            for (Token token : tokens) {
                if (tokenName.equals(token.getName())) {
                    token.moveTo(endPosition);
                    System.out.println(game.getBoard().values());
                    gameDAO.saveGame(game);
                    return true;
                }
            }

            return false;

        } catch (DataAccessException | InconsistentGameDefinitionException exception) {
            System.out.println("ici " + exception.getMessage());
            return false;
        }

    }

    @Override
    public void deleteGame(UUID gameId) {
        gameDAO.deleteById(gameId);
    }

    @Override
    public Game getGameByid(UUID gameId)
            throws DataAccessException, InconsistentGameDefinitionException {

        GameModel gameModel = gameDAO.getGameModel(gameId);
        if (gameModel == null) {
            return null;
        }
        String gameIdentifier = gameModel.getFactory_id();
        int boardSize = gameModel.getBoard_size();

        List<UUID> players = gameDAO.getPlayers(gameId);
        Collection<TokenPosition<UUID>> boardTokens = gameDAO.getBoardTokens(gameId);
        Collection<TokenPosition<UUID>> removedTokens = gameDAO.getRemovedTokens(gameId);

        for(TokenPosition<UUID> tokenPosition : boardTokens) {
            System.out.println("Board Token - Player: " + tokenPosition.owner() +
                    ", Token: " + tokenPosition.tokenName() +
                    ", Position: (" + tokenPosition.x() + ", " + tokenPosition.y() + ")");
        }
        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {

                Game game = gamePlugin.createGameWithIds(gameId,
                        boardSize,
                        players,
                        boardTokens,
                        removedTokens);

                return game;
            }
        }
        throw new IllegalArgumentException("Game name not found");
    }


}
