package fr.cnalps.squaregames.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.cnalps.squaregames.model.SensorModel;

@Repository
public class SensorDao implements SensorsInterfaceDao {

    public static List<SensorModel> sensorsList = new ArrayList<>();

    static{
        sensorsList.add(new SensorModel(1,"Montre Connectée"));
        sensorsList.add(new SensorModel(2,"Ceinture"));
        sensorsList.add(new SensorModel(3,"Bracelet cardio"));
    }

    @Override
    public List<SensorModel> getAllSensors() {
        return sensorsList;
    }

    @Override
    public SensorModel getByID(int id) {
        for(SensorModel sensor : sensorsList){
            if(sensor.getId() == id){
                return sensor;
            }
        }
        return null;
    }

    @Override
    public SensorModel save(SensorModel sensor) {
        int lastSensorId =  sensorsList.getLast().getId();
        sensor.setId(lastSensorId + 1);
        sensorsList.add(sensor);
        System.out.println(sensorsList.get(lastSensorId));
        return sensorsList.getLast();
    }

    @Override
    public SensorModel update(int id, SensorModel sensorUpdated) {
        for(int i = 0; i < sensorsList.size(); i++){
            if(sensorsList.get(i).getId() == id){
                sensorUpdated.setId(id);
                sensorsList.set(i, sensorUpdated);
                return sensorsList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<SensorModel> delete(int id) {
        for(int i = 0; i < sensorsList.size(); i++){
            if(sensorsList.get(i).getId() == id){
                sensorsList.remove(i);
            }
        }
        
        return sensorsList;
    }

}
