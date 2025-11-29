package fr.cnalps.squaregames.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.cnalps.squaregames.model.BoardTokenModel;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.model.RemovedTokenModel;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.Token;
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
    public void deleteById(UUID gameId) throws IllegalArgumentException {
        gameRepository.deleteByUuid(gameId);
    }

    @Override
    public Collection<TokenPosition<UUID>> getBoardTokens(UUID gameUuid) {
        Collection<TokenPosition<UUID>> boardTokens = new ArrayList<>();

        List<BoardTokenModel> boardTokensList = boardTokenRepository.findByGameUuid(gameUuid);

        for (BoardTokenModel boardTokenModel : boardTokensList) {
            PlayerModel playerModel = boardTokenModel.getPlayer();

            UUID userUuid = playerModel.getUuid();
            String tokenName = boardTokenModel.getToken_name();
            int position_x = boardTokenModel.getX();
            int position_y = boardTokenModel.getY();

            boardTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
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
    public Collection<TokenPosition<UUID>> getRemovedTokens(UUID gameUuid) {
        Collection<TokenPosition<UUID>> removedTokens = new ArrayList<>();

        List<RemovedTokenModel> removedTokensList = removedTokenRepository.findByGameUuid(gameUuid);

        for (RemovedTokenModel removedTokenModel : removedTokensList) {
            PlayerModel playerModel = removedTokenModel.getPlayer();

            UUID userUuid = playerModel.getUuid();
            String tokenName = removedTokenModel.getToken_name();
            int position_x = removedTokenModel.getX();
            int position_y = removedTokenModel.getY();

            removedTokens.add(new TokenPosition<>(userUuid, tokenName, position_x, position_y));
        }

        return removedTokens;
    }

    @Override
    public void saveGameModel(GameModel gameModel) {
        if (gameModel == null) {
            return;
        }
        gameRepository.save(gameModel);
    }

    @Override
    public void savePlayerModel(PlayerModel playerModel) {
        if (playerModel == null) {
            return;
        }
        playerRepository.save(playerModel);
    }

    @Transactional
    @Override
    public void saveGame(Game game) {
        UUID uuid = game.getId();
        gameRepository.deleteByUuid(uuid);

        GameModel gameModel = new GameModel();
        gameModel.setUuid(uuid);
        gameModel.setBoard_size(game.getBoardSize());
        gameModel.setFactory_id(game.getFactoryId());
        saveGameModel(gameModel);

        Set<UUID> players = game.getPlayerIds();

        for (UUID player : players) {
            PlayerModel playerModel = new PlayerModel();
            playerModel.setUuid(player);
            playerModel.setGame(gameModel);
            savePlayerModel(playerModel);
        }

        Collection<Token> boardTokens = game.getBoard().values();
        for (Token boardToken : boardTokens) {
            UUID ownerId = boardToken.getOwnerId().orElse(null);
            if (ownerId != null) {
                PlayerModel playerModel = playerRepository.findByUuid(ownerId);

                BoardTokenModel boardTokenModel = new BoardTokenModel();
                boardTokenModel.setPlayer(playerModel);
                boardTokenModel.setToken_name(boardToken.getName());
                CellPosition position = boardToken.getPosition();
                boardTokenModel.setX(position.x());
                boardTokenModel.setY(position.y());
                boardTokenModel.setGame(gameModel);

                saveBoardTokens(boardTokenModel);
            }
        }

        Collection<Token> removedTokens = game.getRemovedTokens();
        for (Token removedToken : removedTokens) {
            UUID ownerId = removedToken.getOwnerId().orElse(null);
            if (ownerId != null) {
                PlayerModel playerModel = playerRepository.findByUuid(ownerId);

                RemovedTokenModel removedTokenModel = new RemovedTokenModel();
                removedTokenModel.setPlayer(playerModel);
                removedTokenModel.setToken_name(removedToken.getName());
                CellPosition position = removedToken.getPosition();
                removedTokenModel.setX(position.x());
                removedTokenModel.setY(position.y());
                removedTokenModel.setGame(gameModel);

                saveRemovedTokens(removedTokenModel);
            }
        }

    }

    private List<BoardTokenModel> getTokensOfPlayer(Game game, PlayerModel playerModel) {
        List<BoardTokenModel> boardTokenModels = new ArrayList<>();
        Collection<Token> tokens = game.getBoard().values();

        for (Token token : tokens) {
            UUID tokenUuid = token.getOwnerId().orElse(null);
            if (tokenUuid != null && tokenUuid.equals(playerModel.getUuid())) {
                CellPosition cellPosition = token.getPosition();
                BoardTokenModel boardTokenModel = new BoardTokenModel();
                boardTokenModel.setPlayer(playerModel);
                boardTokenModel.setX(cellPosition.x());
                boardTokenModel.setY(cellPosition.y());

                boardTokenModels.add(boardTokenModel);
            }
        }

        return boardTokenModels;
    }

    private List<RemovedTokenModel> getRemovedTokensOfPlayer(Game game, PlayerModel playerModel) {
        List<RemovedTokenModel> removedTokenModels = new ArrayList<>();
        Collection<Token> tokens = game.getRemovedTokens();

        for (Token token : tokens) {
            UUID tokenUuid = token.getOwnerId().orElse(null);
            if (tokenUuid != null && tokenUuid.equals(playerModel.getUuid())) {
                CellPosition cellPosition = token.getPosition();
                RemovedTokenModel removedTokenModel = new RemovedTokenModel();
                removedTokenModel.setPlayer(playerModel);
                removedTokenModel.setX(cellPosition.x());
                removedTokenModel.setY(cellPosition.y());

                removedTokenModels.add(removedTokenModel);
            }
        }

        return removedTokenModels;
    }

    private Map<UUID, String> getAllUserOfGame(Game game) {
        Collection<Token> remainingTokens = game.getRemainingTokens();
        Map<CellPosition, Token> board = game.getBoard();

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
        return playerAndName;
    }

    private List<PlayerModel> getListPlayersModel(Map<UUID, String> playerAndName, GameModel gameModel) {
        List<PlayerModel> playerModels = new ArrayList<>();

        for (Map.Entry<UUID, String> entry : playerAndName.entrySet()) {
            UUID uuid = entry.getKey();

            PlayerModel playerModel = new PlayerModel();
            playerModel.setUuid(uuid);
            playerModel.setGame(gameModel);

            playerModels.add(playerModel);
        }

        return playerModels;
    }

    @Transactional
    @Override
    public void saveBoardTokens(BoardTokenModel boardTokenModel) {
        if (boardTokenModel == null) {
            return;
        }
        boardTokenRepository.save(boardTokenModel);
    }

    @Transactional
    @Override
    public void saveRemovedTokens(RemovedTokenModel removedTokenModel) {
        if (removedTokenModel == null) {
            return;
        }
        removedTokenRepository.save(removedTokenModel);
    }

}
