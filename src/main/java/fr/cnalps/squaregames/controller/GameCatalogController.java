package fr.cnalps.squaregames.controller;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.service.GameCatalogInterface;


@RestController
public class GameCatalogController {

    private final GameCatalogInterface gameCatalog;

    public GameCatalogController(GameCatalogInterface gameCatalog){
        this.gameCatalog = gameCatalog;
    }

    @GetMapping("/GameCatalog")
    public Collection<Map<String, String>> getMethodName() {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale.getLanguage());

        return gameCatalog.getGameIdentifier(locale);
    }
    

}
