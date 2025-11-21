package fr.cnalps.squaregames.request;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import fr.le_campus_numerique.square_games.engine.TokenPosition;

public class GameReloadParamsRequest {
    private int boardSize;
    List<UUID> players;
    Collection<TokenPosition<UUID>> boardTokens;
    Collection<TokenPosition<UUID>> removedTokens;

    public int getBoardSize() {
        return boardSize;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public List<UUID> getPlayers() {
        return players;
    }
    public void setPlayers(List<UUID> players) {
        this.players = players;
    }
    public Collection<TokenPosition<UUID>> getBoardTokens() {
        return boardTokens;
    }
    public void setBoardTokens(Collection<TokenPosition<UUID>> boardTokens) {
        this.boardTokens = boardTokens;
    }
    public Collection<TokenPosition<UUID>> getRemovedTokens() {
        return removedTokens;
    }
    public void setRemovedTokens(Collection<TokenPosition<UUID>> removedTokens) {
        this.removedTokens = removedTokens;
    }



}
