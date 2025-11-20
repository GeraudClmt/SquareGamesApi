package fr.cnalps.squaregames.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;

@Repository
public class GameDAO implements GameDAOInterface {

    private final Map<UUID, Game> games;

    public GameDAO() {
        games = new HashMap<>();
    }

    @Override
    public Game getGameById(UUID gameId) throws IllegalArgumentException {
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }

        return game;
    }

    @Override
    public void save(Game game) {
        games.put(game.getId(), game);
    }

    @Override
    public void deleteById(UUID gameId) throws IllegalArgumentException {
        Game game = games.remove(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
    }

    @Override
    public Map<UUID, Game> findAll() {
        return games;
    }

    @Override
    public Map<CellPosition, Token> getBoardByGameId(UUID gameId) throws IllegalArgumentException {
        Game game = getGameById(gameId);

        return game.getBoard();
    }

    @Override
    public Collection<Token> getRemainingToken(UUID gameId) throws IllegalArgumentException {
        Game game = getGameById(gameId);
        return game.getRemainingTokens();
    }

    @Override
    public void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition)
            throws InvalidPositionException {
        Map<CellPosition, Token> currentBoard = getBoardByGameId(gameId);
        Token curentToken = currentBoard.get(startPosition);
        curentToken.moveTo(endPosition);
    }

    @Override
    public void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition) throws IllegalArgumentException, InvalidPositionException {
        Game game = getGameById(gameId);
        Collection<Token> remainingTokens = game.getRemainingTokens();

        for (Token token : remainingTokens) {
            if (token.getName().equals(tokenName)) {
                token.moveTo(endPosition);
                return;
            }
        }
        throw new IllegalArgumentException("Token name not found");
    }

}
