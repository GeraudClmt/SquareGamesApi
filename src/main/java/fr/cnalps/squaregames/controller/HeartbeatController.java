package fr.cnalps.squaregames.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.cnalps.squaregames.dao.SensorDao;
import fr.cnalps.squaregames.model.SensorModel;
import fr.cnalps.squaregames.service.HeartbeatSensor;




@RestController
public class HeartbeatController {

    @Autowired
    private HeartbeatSensor heartbeatSensor;

    private SensorDao sensorDao;

    public HeartbeatController(SensorDao sensorDao){
        this.sensorDao = sensorDao;
    }

    @GetMapping("/heartbeat")
	public int getHeartbeat(){
		return heartbeatSensor.getInteger();
	}

    @GetMapping("/sensors")
    public List<SensorModel> getAllSensors() {
        return sensorDao.getAllSensors();
    }
    
    @GetMapping("/sensors/{id}")
    public SensorModel getSensorById(@PathVariable int id) {
        return sensorDao.getByID(id);
    }
    
    @PutMapping("/sensors/{id}")
    public SensorModel updateSensor(@PathVariable int id, @RequestBody SensorModel sensorModel) {
        return sensorDao.update(id, sensorModel);
    }

    @PostMapping("/sensors")
    public SensorModel createSensor(@RequestBody SensorModel sensorModel) {
        return sensorDao.save(sensorModel);
    }
    
    @DeleteMapping("/sensors/{id}")
    public List<SensorModel> deleteSensor(@PathVariable int id){
        return sensorDao.delete(id);
    }

}
