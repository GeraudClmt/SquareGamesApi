package fr.cnalps.squaregames.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.service.GameCatalogInterface;


@RestController
public class GameCatalogController {

    @Autowired
    private GameCatalogInterface gameCatalog;

    @GetMapping("/GameCatalog")
    public Collection<String> getMethodName() {
        return gameCatalog.getGameIdentifier();
    }
    

}
