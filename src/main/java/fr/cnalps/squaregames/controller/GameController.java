package fr.cnalps.squaregames.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/game")
public class GameController {

    @PostMapping("/create")
    public String create(@RequestBody String gameId) {
        //TODO: process POST request
        
        return null;
    }
    

}
