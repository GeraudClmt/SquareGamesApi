package fr.cnalps.squaregames.model;

public class SensorModel {
    private int id;
    private String name;

    public SensorModel(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return "Sensor{" + 
                    "id=" + id +
                    ", nom=" + name +
                    "}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
