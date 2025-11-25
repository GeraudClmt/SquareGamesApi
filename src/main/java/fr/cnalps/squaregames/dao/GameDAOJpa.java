package fr.cnalps.squaregames.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.BoardTokenModel;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.model.RemovedTokenModel;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Repository
@Primary
public class GameDAOJpa implements GameDAOInterface {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    BoardTokenRepository boardTokenRepository;
    @Autowired
    RemovedTokenRepository removedTokenRepository;

    @Override
    public Integer deleteById(UUID gameId) throws IllegalArgumentException {
        return gameRepository.deleteByUuid(gameId);
    }

    @Override
    public List<TokenPosition<UUID>> getBoardTokens(List<UUID> players) {
        List<TokenPosition<UUID>> boardTokens = new ArrayList<>();

        for (UUID playerUuid : players) {
            List<BoardTokenModel> boardTokensList = boardTokenRepository.findByPlayerUuid(playerUuid);

            for (BoardTokenModel boardTokenModel : boardTokensList) {
                PlayerModel playerModel = boardTokenModel.getPlayer();

                UUID userUuid = playerModel.getUuid();
                String tokenName = playerModel.getToken_name();
                int position_x = boardTokenModel.getX();
                int position_y = boardTokenModel.getY();

                boardTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
            }
        }

        return boardTokens;
    }

    @Override
    public GameModel getGameModel(UUID gameId) {
        return gameRepository.findByUuid(gameId);
    }

    @Override
    public List<UUID> getPlayers(UUID gameUuid) {
        return playerRepository.findPlayerUuidsByGameUuid(gameUuid);
    }

    @Override
    public List<TokenPosition<UUID>> getRemovedTokens(List<UUID> players) {
        List<TokenPosition<UUID>> removedTokens = new ArrayList<>();

        for (UUID playerUuid : players) {
            List<RemovedTokenModel> removedTokensList = removedTokenRepository.findByPlayerUuid(playerUuid);

            for (RemovedTokenModel removedTokenModel : removedTokensList) {
                PlayerModel playerModel = removedTokenModel.getPlayer();

                UUID userUuid = playerModel.getUuid();
                String tokenName = playerModel.getToken_name();
                int position_x = removedTokenModel.getX();
                int position_y = removedTokenModel.getY();

                removedTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
            }
        }

        return removedTokens;
    }

    @Override
    public void moveTokenByRemaining(UUID gameId, String tokenName, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void moveTokenWithStartPosition(UUID gameId, CellPosition startPosition, CellPosition endPosition)
            throws IllegalArgumentException, InvalidPositionException {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame(GameModel gameModel) throws IllegalArgumentException {
        if (gameModel == null) {
            return;
        }
        gameRepository.save(gameModel);
    }

    @Override
    public void savePlayer(PlayerModel playerModel) {
        if (playerModel == null) {
            return;
        }

        playerRepository.save(playerModel);
    }

}
