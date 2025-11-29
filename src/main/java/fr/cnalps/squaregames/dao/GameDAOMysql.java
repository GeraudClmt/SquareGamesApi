package fr.cnalps.squaregames.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.BoardTokenModel;
import fr.cnalps.squaregames.model.GameModel;
import fr.cnalps.squaregames.model.PlayerModel;
import fr.cnalps.squaregames.model.RemovedTokenModel;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.InvalidPositionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;

@Repository
public class GameDAOMysql implements GameDAOInterface {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    public void deleteById(UUID gameId) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return;
    }

    @Override
    public List<TokenPosition<UUID>> getBoardTokens(UUID gameUuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameModel getGameModel(UUID gameId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UUID> getPlayers(UUID gameUuid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TokenPosition<UUID>> getRemovedTokens(UUID gameUuid) {
        // TODO Auto-generated method stub
        return null;
    }
    

    @Override
    public void saveGame(Game game) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void saveGameModel(GameModel gameModel) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void savePlayerModel(PlayerModel playerModel) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void saveBoardTokens(BoardTokenModel boardTokenModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveRemovedTokens(RemovedTokenModel removedTokenModel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
}
