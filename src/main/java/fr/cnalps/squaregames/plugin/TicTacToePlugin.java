package fr.cnalps.squaregames.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@Component
public class TicTacToePlugin implements GamePluginInterface {

    TicTacToeGameFactory ticTacToeGameFactory;
    private final int defaultPlayerCount;
    private final int defaultBoardSize;
    MessageSource messageSource;

    public TicTacToePlugin(TicTacToeGameFactory ticTacToeGameFactory,
            MessageSource messageSource,
            @Value("${squaregames.tictactoe.default-player-count}") int defaultPlayerCount,
            @Value("${squaregames.tictactoe.default-board-size}") int defaultBoardSize) {

        this.ticTacToeGameFactory = ticTacToeGameFactory;
        this.messageSource = messageSource;
        this.defaultPlayerCount = defaultPlayerCount;
        this.defaultBoardSize = defaultBoardSize;

    }

    @Override
    public Game createGame(int boardSize, Set<UUID> players) {
        if (boardSize == 0) {
            boardSize = defaultBoardSize;
        }

        return ticTacToeGameFactory.createGame(boardSize, players);
    }

    @Override
    public String getName(Locale local) {
        return messageSource.getMessage("titactoe.name", null, "TicTacToe", local);
    }

    @Override
    public String getGamePluginId() {
        return ticTacToeGameFactory.getGameFactoryId();
    }

    @Override
    public Game createGameWithIds(UUID gameId, int boardSize, List<UUID> players,
            Collection<TokenPosition<UUID>> boardTokens, Collection<TokenPosition<UUID>> removedTokens)
            throws InconsistentGameDefinitionException {
        return ticTacToeGameFactory.createGameWithIds(gameId, boardSize, players, boardTokens, removedTokens);
    }

}
