package fr.cnalps.squaregames.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

public interface GamePluginInterface {
    Game createGame(int playerCount, int boardSize);

    String getName(Locale local);

    String getGamePluginId();

    Game createGameWithIds(UUID gameId, int boardSize, List<UUID> players, Collection<TokenPosition<UUID>> boardTokens,
            Collection<TokenPosition<UUID>> removedTokens)throws InconsistentGameDefinitionException;
}
