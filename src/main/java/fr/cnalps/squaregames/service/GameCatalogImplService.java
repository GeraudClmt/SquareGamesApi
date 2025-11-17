package fr.cnalps.squaregames.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@Service
public class GameCatalogImplService implements GameCatalogInterface {

    private final TicTacToeGameFactory ticTacToeGameFactory;
    private final ConnectFourGameFactory connectFourGameFactory;
    private final TaquinGameFactory taquinGameFactory;

    public GameCatalogImplService(TicTacToeGameFactory ticTacToeGameFactory, ConnectFourGameFactory connectFourGameFactory, TaquinGameFactory taquinGameFactory){
        this.ticTacToeGameFactory = ticTacToeGameFactory;
        this.connectFourGameFactory = connectFourGameFactory;
        this.taquinGameFactory = taquinGameFactory;
    }

    @Override
    public Collection<String> getGameIdentifier() {
        List<String> gameIdentifierList = new ArrayList<>();
        gameIdentifierList.add(ticTacToeGameFactory.getGameFactoryId());
        gameIdentifierList.add(connectFourGameFactory.getGameFactoryId());
        gameIdentifierList.add(taquinGameFactory.getGameFactoryId());

        return gameIdentifierList;
    }

}
