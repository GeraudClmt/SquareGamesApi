package fr.cnalps.squaregames.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.Token;
import fr.le_campus_numerique.square_games.engine.TokenPosition;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@Repository
@Primary
public class GameDAOMysql implements GameDAOInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Game getGameById(UUID gameId) throws DataAccessException, InconsistentGameDefinitionException {
        Integer boardSize;
        List<UUID> players = new ArrayList<>();
        List<TokenPosition<UUID>> boardTokens = new ArrayList<>();
        List<TokenPosition<UUID>> removedTokens = new ArrayList<>();
        String stringId = gameId.toString();

        boardSize = jdbcTemplate.queryForObject("SELECT board_size FROM games WHERE uuid = ?", Integer.class,
                gameId.toString());

        List<Map<String, Object>> playersSQLResponse = jdbcTemplate.queryForList(
                "SELECT players.uuid FROM players INNER JOIN squaregames.games ON players.game_id = games.id WHERE games.uuid = ?",
                stringId);

        for (Map<String, Object> row : playersSQLResponse) {
            players.add(UUID.fromString((String) row.get("uuid")));
        }

        for (UUID playerUuid : players) {
            List<Map<String, Object>> boardTokenSQLResponse = jdbcTemplate.queryForList(
                    "SELECT * FROM board_tokens inner join squaregames.players p on board_tokens.player_id = p.id WHERE p.uuid = ?",
                    playerUuid.toString());

            for (Map<String, Object> row : boardTokenSQLResponse) {
                UUID userUuid = UUID.fromString((String) row.get("uuid"));
                String tokenName = (String) row.get("token_name");
                int position_x = (int) row.get("x");
                int position_y = (int) row.get("y");

                boardTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
            }

            List<Map<String, Object>> removedTokensSQLResponse = jdbcTemplate.queryForList(
                    "SELECT * FROM removed_tokens inner join squaregames.players p on removed_tokens.player_id = p.id WHERE p.uuid = ?",
                    playerUuid.toString());

            for (Map<String, Object> row : removedTokensSQLResponse) {
                UUID userUuid = UUID.fromString((String) row.get("uuid"));
                String tokenName = (String) row.get("token_name");
                int position_x = (int) row.get("x");
                int position_y = (int) row.get("y");

                removedTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
            }
        }

        TicTacToeGameFactory ticTacToeGameFactory = new TicTacToeGameFactory();

        Game gameReloaded = ticTacToeGameFactory.createGameWithIds(gameId, boardSize, players, boardTokens,
                removedTokens);
        return gameReloaded;
    }

    @Override
    public void save(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteById(UUID gameId) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<UUID, Game> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<CellPosition, Token> getBoardByGameId(UUID gameId) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Token> getRemainingToken(UUID gameId) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
