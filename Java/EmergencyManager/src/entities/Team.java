package entities;

import java.util.ArrayList;

public class Team {

    private int id;
    private String name;
    private ArrayList agentsId;
    private ArrayList vehiclesId;
    private float coeff;

    public Team(){}

    public Team(int id){ this.id = id; }

    public Team(int id, String name, ArrayList agentsId, ArrayList vehiclesId, int coeff){
        this.id = id;
        this.name = name;
        this.agentsId = agentsId;
        this.vehiclesId = vehiclesId;
        this.coeff = coeff;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList getAgentsId() {
        return agentsId;
    }

    public ArrayList getVehiclesId() {
        return vehiclesId;
    }

    public float getCoeff() {
        return coeff;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgentsId(ArrayList agentsId) {
        this.agentsId = agentsId;
    }

    public void setVehiclesId(ArrayList vehiclesId) {
        this.vehiclesId = vehiclesId;
    }

    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }
}
