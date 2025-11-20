package fr.cnalps.squaregames.service;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface GameCatalogInterface {
    Collection<Map<String, String>> getGameIdentifier(Locale locale);
}
