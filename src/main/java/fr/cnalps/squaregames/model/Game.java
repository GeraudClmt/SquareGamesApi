package fr.cnalps.squaregames.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private UUID gameUuid;
    private int boardSize;
    private String gameIdentifier;

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public UUID getGameUuid() {
        return gameUuid;
    }
    public void setGameUuid(UUID gameUuid) {
        this.gameUuid = gameUuid;
    }
    public int getBoardSize() {
        return boardSize;
    }
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public String getGameIdentifier() {
        return gameIdentifier;
    }
    public void setGameIdentifier(String gameIdentifier) {
        this.gameIdentifier = gameIdentifier;
    }

    
    
}
