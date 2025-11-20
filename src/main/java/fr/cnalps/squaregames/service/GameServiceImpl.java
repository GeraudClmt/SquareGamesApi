package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

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
    private final Collection<Game> games;

    public GameServiceImpl(List<GamePluginInterface> gamePluginInstefaceList) {
        this.gamePluginInstefaceList = gamePluginInstefaceList;
        games = new ArrayList<>();
    }

    @Override
    public UUID create(GameCreationParamsRequest gameCreationParamsRequest) throws IllegalArgumentException {
        String gameName = gameCreationParamsRequest.getGameName();
        int playerCount = gameCreationParamsRequest.getPlayerCount();
        int boardSize = gameCreationParamsRequest.getBoardSize();

        for(GamePluginInterface gamePlugin : gamePluginInstefaceList){
            if(gameName.equals(gamePlugin.getGamePluginId())){
                Game game = gamePlugin.createGame(playerCount, boardSize);
                games.add(game);
                return game.getId();
            }
        }

        throw new IllegalArgumentException("Game not found");
        
    }

    @Override
    public GameStatus getStatus(UUID gameId) throws IllegalArgumentException {
        for (Game game : games) {
            if (gameId.toString().equals(game.getId().toString())) {
                return game.getStatus();
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y)
            throws IllegalArgumentException {
        CellPosition cellPositions = new CellPosition(x, y);
        for (Game game : games) {
            if (gameId.toString().equals(game.getId().toString())) {
                Map<CellPosition, Token> currentBoard = game.getBoard();

                for (CellPosition positions : currentBoard.keySet()) {
                    if (positions.x() == cellPositions.x() && positions.y() == cellPositions.y()) {
                        return currentBoard.get(positions).getAllowedMoves();
                    }
                }
                throw new IllegalArgumentException("Token name not found");
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName)
            throws IllegalArgumentException {
        for (Game game : games) {
            if (gameId.toString().equals(game.getId().toString())) {

                Collection<Token> remainingTokens = game.getRemainingTokens();
                for (Token token : remainingTokens) {
                    if (token.getName().equals(tokenName)) {
                        return token.getAllowedMoves();
                    }
                }
                throw new IllegalArgumentException("Token name not found");
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public boolean moveToken(UUID gameId, GameMoveTokenParamsRequest gameMoveTokenParamsRequest)
            throws IllegalArgumentException, InvalidPositionException {
        CellPosition startPosition = gameMoveTokenParamsRequest.getStartPosition();
        CellPosition endPosition = gameMoveTokenParamsRequest.getEndPosition();
        String tokenName = gameMoveTokenParamsRequest.getTokenName();

        if (startPosition != null) {
            for (Game game : games) {
                if (gameId.equals(game.getId())) {
                    Map<CellPosition, Token> currentBoard = game.getBoard();

                    Token curentToken = currentBoard.get(startPosition);
                    curentToken.moveTo(endPosition);
                    return true;
                }
            }
            throw new IllegalArgumentException("Game not found");
        }

        for (Game game : games) {
            if (gameId.equals(game.getId())) {
                Collection<Token> currentBoard = game.getRemainingTokens();

                for (Token token : currentBoard) {
                    if (token.getName().equals(tokenName)) {
                        token.moveTo(endPosition);
                        return true;
                    }
                }
                throw new IllegalArgumentException("Token name not found");
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public Game getGame(UUID gameId) throws IllegalArgumentException {
        for (Game game : games) {
            if (gameId.toString().equals(game.getId().toString())) {
                return game;
            }
        }
        throw new IllegalArgumentException("Game not found");
    }

    @Override
    public void deleteGame(UUID gameId) throws IllegalArgumentException {
        Game gameToRemove = null;
        for(Game game : games){
            if(game.getId().equals(gameId)){
                gameToRemove = game;
                break;
            }
        }

        if(gameToRemove == null){
            throw new IllegalArgumentException("Game not found");
        }

        games.remove(gameToRemove);

    }


}
