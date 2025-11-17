package fr.cnalps.squaregames.config;

import org.springframework.context.annotation.Bean;

import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    TicTacToeGameFactory ticTacToeGameFactory(){
        return new TicTacToeGameFactory();
    }

    @Bean
    ConnectFourGameFactory connectFourGameFactory(){
        return new ConnectFourGameFactory();
    }

    @Bean
    TaquinGameFactory taquinGameFactory(){
        return new TaquinGameFactory();
    }
}
