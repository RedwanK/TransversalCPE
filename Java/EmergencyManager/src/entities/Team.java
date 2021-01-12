package entities;

import java.util.ArrayList;

public class Team implements Comparable<Team>{

    private int id;
    private String name;
    private int num_age;
    private int num_veh;
    private float coeff;

    public Team(){}

    public Team(int id){ this.id = id; }

    public Team(int id, String name,int num_age,int num_veh, float coeff){
        this.id = id;
        this.name = name;
        this.num_age = num_age;
        this.num_veh = num_veh;
        this.coeff = coeff;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNum_age() { return num_age; }

    public int getNum_veh() { return num_veh; }

    public float getCoeff() {
        return coeff;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum_age(int num_age) { this.num_age = num_age; }

    public void setNum_veh(int num_veh) { this.num_veh = num_veh; }

    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }

    @Override
    public int compareTo(Team t) {
        return ((Float)this.getCoeff()).compareTo(t.getCoeff());
    }
}
