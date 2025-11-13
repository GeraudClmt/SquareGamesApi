package fr.cnalps.squaregames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.service.HeartbeatSensor;


@RestController
public class HeartbeatController {

    @Autowired
    private HeartbeatSensor heartbeatSensor;

    @GetMapping("/heartbeat")
	public int getHeartbeat(){
		return heartbeatSensor.getInteger();
	}

}
