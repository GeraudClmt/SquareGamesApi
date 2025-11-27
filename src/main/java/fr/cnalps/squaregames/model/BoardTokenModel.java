package fr.cnalps.squaregames.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BoardTokenModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private PlayerModel player;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public PlayerModel getPlayer() { return player; }
    public void setPlayer(PlayerModel player) { this.player = player; }

}
