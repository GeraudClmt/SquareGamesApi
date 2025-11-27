package fr.cnalps.squaregames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class GameModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private UUID uuid;
    private int board_size;
    private String factory_id;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerModel> players = new ArrayList<>();

    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public int getBoard_size() {
        return board_size;
    }
    public void setBoard_size(int board_size) {
        this.board_size = board_size;
    }
    public String getFactory_id() {
        return factory_id;
    }
    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    
    
    
}
