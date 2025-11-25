package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.cnalps.squaregames.dao.GameDAOInterface;
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

    @Override
    public UUID create(GameCreationParamsRequest gameCreationParamsRequest) throws IllegalArgumentException {
        String gameIdentifier = gameCreationParamsRequest.getGameName();

        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {
                Game game = gamePlugin.createGame(gameCreationParamsRequest.getPlayerCount(),
                        gameCreationParamsRequest.getBoardSize());
                
                gameDAO.saveGame(game);

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
        try {
            Game game = getGameByid(gameId);

            CellPosition startPosition = gameMoveTokenParamsRequest.getStartPosition();
            CellPosition endPosition = gameMoveTokenParamsRequest.getEndPosition();
            String tokenName = gameMoveTokenParamsRequest.getTokenName();

            Collection<Token> tokens = new ArrayList<>();

            if (startPosition == null) {
                tokens = game.getRemainingTokens();
            }else{
                tokens = game.getBoard().values();
            }
            
            for(Token token : tokens){
                    if(tokenName.equals(token.getName())){
                        token.moveTo(endPosition);
                        gameDAO.saveGame(game);
                        return true;
                    }
                }

            return false;

        } catch (DataAccessException | InconsistentGameDefinitionException exception) {
            return false;
        }

    }

    @Override
    public Integer deleteGame(UUID gameId) {
        return gameDAO.deleteById(gameId);
    }

    @Override
    public Game getGameByid(UUID gameId) throws DataAccessException, InconsistentGameDefinitionException {
        System.out.println("Dans get 125");
        GameModel gameModel = gameDAO.getGameModel(gameId);
        System.out.println("Get le model" );
        String gameIdentifier = gameModel.getFactory_id();
        System.out.println("get identifier");
        int boardSize = gameModel.getBoard_size();
        System.out.println("Dans get 129");
        List<UUID> players = gameDAO.getPlayers(gameId);

        List<TokenPosition<UUID>> boardTokens = gameDAO.getBoardTokens(players);
        System.out.println("Dans get 133");

        for (TokenPosition<UUID> tokenPosition : boardTokens) {
        System.out.println("Board Token - User UUID: " + tokenPosition.owner() + ",Token Name: "
        + tokenPosition.tokenName() + ", Position: (" + tokenPosition.x() + ", " +
        tokenPosition.y() + ")");
        }

        List<TokenPosition<UUID>> removedTokens = gameDAO.getRemovedTokens(players);
        System.out.println("Je passe " + removedTokens.size());

        for (GamePluginInterface gamePlugin : gamePluginInstefaceList) {
            if (gameIdentifier.equals(gamePlugin.getGamePluginId())) {
                System.out.println("ligne 148");

                System.out.println(gameId);
                System.out.println(boardSize);
                System.out.println(players);
                System.out.println(boardTokens);
                System.out.println(removedTokens);

                Game game = gamePlugin.createGameWithIds(gameId,
                        boardSize,
                        players,
                        boardTokens,
                        removedTokens);

                System.out.println(game.getBoard());
                return game;
            }
        }
        throw new IllegalArgumentException("Game name not found");
    }

}