package fr.cnalps.squaregames.dao;

import java.util.List;

import fr.cnalps.squaregames.model.SensorModel;

public interface SensorsInterfaceDao {
    List<SensorModel> getAllSensors();
    SensorModel getByID(int id);
    SensorModel save(SensorModel sensor);
    SensorModel update(int id, SensorModel sensorUpdated);
    List<SensorModel> delete(int id);
}
