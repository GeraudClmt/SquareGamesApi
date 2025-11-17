package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameStatus;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@Service
public class GameServiceImpl implements GameServiceInterface {

    @Autowired
    TicTacToeGameFactory ticTacToeGameFactory;
    @Autowired
    ConnectFourGameFactory connectFourGameFactory;
    @Autowired
    TaquinGameFactory taquinGameFactory;

    private final Collection<Game> games;

    public GameServiceImpl() {
        games = new ArrayList<>();
    }

    @Override
    public UUID create(String gameName, int playerCount, int boardSize) throws IllegalArgumentException {

        Game game = switch (gameName) {
            case "tictactoe" -> ticTacToeGameFactory.createGame(playerCount, boardSize);
            case "connect4" -> connectFourGameFactory.createGame(playerCount, boardSize);
            case "15 puzzle" -> taquinGameFactory.createGame(playerCount, boardSize);
            default -> null;
        };

        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }

        games.add(game);
        return game.getId();
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
    public Set<CellPosition> getAllowedMovesWithCellPositions(UUID gameId, int x, int y) throws IllegalArgumentException {
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
    public Set<CellPosition> getAllowedMovesWithRemainingToken(UUID gameId, String tokenName) throws IllegalArgumentException {
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
    public boolean moveToken(UUID tokenId, CellPosition startPositionn, CellPosition endPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
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

}
