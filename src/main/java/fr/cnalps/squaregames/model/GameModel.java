package fr.cnalps.squaregames.model;

import java.util.UUID;

public class GameModel {
    private final UUID gameUuid;
    private final int boardSize;
    private final String gameIdentifier;

    public GameModel(UUID gameId, int boardSize, String gameIdentifier) {
        this.gameUuid = gameId;
        this.boardSize = boardSize;
        this.gameIdentifier = gameIdentifier;
    }

    public UUID getGameUuid() {
        return gameUuid;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }
}
