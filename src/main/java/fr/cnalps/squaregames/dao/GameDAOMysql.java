package fr.cnalps.squaregames.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.Game;
import fr.cnalps.squaregames.model.Player;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Repository
@Primary
public class GameDAOMysql implements GameDAOInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Game getGameModel(UUID gameId) {
        String stringId = gameId.toString();

        Integer boardSize = jdbcTemplate.queryForObject("SELECT board_size FROM games WHERE uuid = ?", Integer.class,
                stringId);

        String factoryId = jdbcTemplate.queryForObject("SELECT factory_id FROM games WHERE uuid = ?", String.class,
                stringId);

        if (boardSize == null || factoryId == null) {
            return null;
        }

        return new Game(gameId, boardSize, factoryId);
    }

    @Override
    public List<UUID> getPlayers(UUID gameUuid) {
        List<UUID> players = new ArrayList<>();

        List<Map<String, Object>> playersSQLResponse = jdbcTemplate.queryForList(
                "SELECT players.uuid FROM players INNER JOIN squaregames.games ON players.game_id = games.id WHERE games.uuid = ?",
                gameUuid.toString());

        for (Map<String, Object> row : playersSQLResponse) {
            players.add(UUID.fromString((String) row.get("uuid")));
        }

        return players;

    }

    @Override
    public List<TokenPosition<UUID>> getBoardTokens(List<UUID> players) {
        List<TokenPosition<UUID>> boardTokens = new ArrayList<>();

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
        }

        return boardTokens;
    }

    @Override
    public List<TokenPosition<UUID>> getRemovedTokens(List<UUID> players) {
        List<TokenPosition<UUID>> removedTokens = new ArrayList<>();

        for (UUID playerUuid : players) {
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

        return removedTokens;
    }

    @Override
    public int saveGame(Game gameModel) throws IllegalArgumentException {
        UUID gameUuid = gameModel.getGameUuid();
        String sqlGame = "INSERT INTO `squaregames`.`games` (`uuid`, `board_size`, `factory_id`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlGame, gameUuid.toString(), gameModel.getBoardSize(), gameModel.getGameIdentifier());
        Integer id = jdbcTemplate.queryForObject("SELECT id FROM games WHERE uuid = ?", Integer.class,
                gameUuid.toString());

        if (id == null) {
            throw new IllegalArgumentException("Erreur enregistrement du jeux en base de donnée");
        }
        return (int) id;
    }

    @Override
    public int savePlayer(Player playerModel) throws IllegalArgumentException {
        String sql = "INSERT INTO players (uuid, game_id, token_name) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, playerModel.getPlayerUuid().toString());
            ps.setInt(2, playerModel.getGameId());
            ps.setString(3, playerModel.getTokenName());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalArgumentException("Erreur lors de l'enregistrement du joueur");
        }

        return key.intValue();
    }

    @Override
    public void deleteById(UUID gameId) {
        String sqlBoardToken = "DELETE FROM games where uuid = ?";
        jdbcTemplate.update(sqlBoardToken, gameId.toString());
    }

    @Override
    public void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {

        Integer playerId = jdbcTemplate.queryForObject("SELECT id FROM players WHERE uuid = ?", Integer.class,
                gameId.toString());

        String sql = "UPDATE board_tokens SET x = ?, y = ? WHERE player_id = ? AND x = ? AND y = ?";

        jdbcTemplate.update(sql, endPosition.x(), endPosition.y(), playerId, startPosition.x(), startPosition.y());

    }

    @Override
    public void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {
        String sql = "SELECT players.id FROM players INNER JOIN games ON players.game_id = games.id WHERE games.uuid = ? AND players.token_name = ?";
        Integer playerId = jdbcTemplate.queryForObject(sql, Integer.class, gameId.toString(), tokenName);

        String sqlMoove = "INSERT INTO board_tokens (player_id, x, y) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlMoove, playerId, endPosition.x(), endPosition.y());
        
    }
}
