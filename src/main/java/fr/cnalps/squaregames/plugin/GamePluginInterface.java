package fr.cnalps.squaregames.plugin;

import java.util.Locale;

import fr.le_campus_numerique.square_games.engine.Game;


public interface GamePluginInterface{
    Game createGame(int playerCount, int boardSize);
    String getName(Locale local);
    String getGamePluginId();
}
