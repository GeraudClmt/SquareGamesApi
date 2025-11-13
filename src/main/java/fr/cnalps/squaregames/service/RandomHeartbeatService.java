package fr.cnalps.squaregames.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomHeartbeatService implements HeartbeatSensor {
    private final Random rand;

    public RandomHeartbeatService(){
        rand = new Random();
    }

    @Override
    public int getInteger() {
        return rand.nextInt(40, 231);
    }

}
