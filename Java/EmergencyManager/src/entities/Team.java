package entities;

import java.util.ArrayList;

public class Team {

    private int id;
    private String name;
    private float coeff;

    public Team(){}

    public Team(int id){ this.id = id; }

    public Team(int id, String name, float coeff){
        this.id = id;
        this.name = name;
        this.coeff = coeff;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }
}
