package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import fr.cnalps.squaregames.plugin.GamePluginInterface;

@Service
public class GameCatalogImplService implements GameCatalogInterface {

    private final List<GamePluginInterface> gamePluginInstefaceList;   

    public GameCatalogImplService(List<GamePluginInterface> gamePluginInstefaceList){
        this.gamePluginInstefaceList = gamePluginInstefaceList;
    }

    @Override
    public Collection<Map<String, String>> getGameIdentifier(Locale locale) {
        List<Map<String, String>> gameIdentifierList = new ArrayList<>();
        
        for(GamePluginInterface gamePlugin : gamePluginInstefaceList){
            gameIdentifierList.add(new HashMap<String, String>(){{
                                        put("id", gamePlugin.getGamePluginId());
                                        put("name", gamePlugin.getName(locale));
                                    }});
        }

        return gameIdentifierList;
    }

}
