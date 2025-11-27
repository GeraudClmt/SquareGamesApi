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
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;

@Component
public class ConnectFourPlugin implements GamePluginInterface{

    ConnectFourGameFactory connectFourGameFactory;
    private final int defaultPlayerCount;
    private final int defaultBoardSize;
    MessageSource messageSource;

    public ConnectFourPlugin(ConnectFourGameFactory connectFourGameFactory,
            MessageSource messageSource,
            @Value("${squaregames.connectfour.default-player-count}") int defaultPlayerCount,
            @Value("${squaregames.connectfour.default-board-size}") int defaultBoardSize) {
        
        this.connectFourGameFactory = connectFourGameFactory; 
        this.messageSource = messageSource;       
        this.defaultPlayerCount = defaultPlayerCount;
        this.defaultBoardSize = defaultBoardSize;
        
    }

    @Override
    public Game createGame(int boardSize, Set<UUID> players) {

        if(boardSize == 0){
            boardSize = defaultBoardSize;
        }

        return connectFourGameFactory.createGame(boardSize, players);
    }

    @Override
    public String getName(Locale local) {
        return messageSource.getMessage("connectfour.name", null, "Connect4" ,local);
    }

    @Override
    public String getGamePluginId() {
        return connectFourGameFactory.getGameFactoryId();
    }

    @Override
    public Game createGameWithIds(UUID gameId, int boardSize, List<UUID> players, Collection<TokenPosition<UUID>> boardTokens, Collection<TokenPosition<UUID>> removedTokens) throws InconsistentGameDefinitionException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
