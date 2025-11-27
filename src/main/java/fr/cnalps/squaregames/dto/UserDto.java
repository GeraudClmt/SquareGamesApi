package fr.cnalps.squaregames.dto;

import java.util.UUID;

public class UserDto {
    private String name;
    private UUID uuid;

    public UserDto(String name, UUID uuid){
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
