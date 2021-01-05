package entities;

public class Vehicle {
    private int id;
    private int type;
    private float fuel;
    private int site_id;

    public Vehicle() {}

    public Vehicle(int id) {
        this.id = id;
    }

    public Vehicle(int id, int type, float fuel, int site_id) {
        this.id = id;
        this.type = type;
        this.fuel = fuel;
        this.site_id = site_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public int getSite_id() {
        return site_id;
    }

    public void setSite_id(int site_id) {
        this.site_id = site_id;
    }
}
