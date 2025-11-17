package fr.cnalps.squaregames.request;

public class GameCreationParamsRequest {
    private String gameName;
    private int playerCount;
    private int boardSize;

    public String getGameName() {
        return gameName;
    }

    public void setgameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }



}
