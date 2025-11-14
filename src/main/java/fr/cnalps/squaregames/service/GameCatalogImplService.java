package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@Service
public class GameCatalogImplService implements GameCatalogInterface {

    @Autowired
    private TicTacToeGameFactory ticTacToeGameFactory;

    @Override
    public Collection<String> getGameIdentifier() {
        List<String> gameIdentifierList = new ArrayList<>();
        gameIdentifierList.add(ticTacToeGameFactory.getGameFactoryId());

        return gameIdentifierList;
    }

}
