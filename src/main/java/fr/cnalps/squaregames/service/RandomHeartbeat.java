package fr.cnalps.squaregames.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomHeartbeat implements HeatbeatSensor {
    private final Random rand;

    public RandomHeartbeat(){
        rand = new Random();
    }

    @Override
    public int getInteger() {
        return rand.nextInt(40, 231);
    }

}
