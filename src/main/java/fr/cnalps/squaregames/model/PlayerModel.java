package fr.cnalps.squaregames.model;

import java.util.UUID;

public class PlayerModel {
    private final UUID playerUuid;
    private final int gameId;
    private final String tokenName;

    public PlayerModel(UUID playerUuid, int gameId, String tokenName){
        this.gameId = gameId;
        this.playerUuid = playerUuid;
        this.tokenName = tokenName;
    }

    
    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public int getGameId() {
        return gameId;
    }

    public String getTokenName() {
        return tokenName;
    }

}
