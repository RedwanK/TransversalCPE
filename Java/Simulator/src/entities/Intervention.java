package entities;

public class Intervention{
    private int id;
    private float coeff;
    private int num_veh;
    private int num_age;
    private int incidentId;

    public Intervention() {}

    public Intervention(int id) {
        this.id = id;
    }

    public Intervention(int id, float coeff, int num_veh, int num_age, int incidentId) {
        this.id = id;
        this.coeff = coeff;
        this.num_veh = num_veh;
        this.num_age = num_age;
        this.incidentId = incidentId;
    }

    public int getId() {
        return id;
    }


    public float getCoeff() {
        return coeff;
    }

    public int getNum_veh() {
        return num_veh;
    }

    public int getNum_age() {
        return num_age;
    }


    public void setCoeff(float coeff) {
        this.coeff = coeff;
    }

    public void setNum_veh(int num_veh) {
        this.num_veh = num_veh;
    }

    public void setNum_age(int num_age) {
        this.num_age = num_age;
    }

    public int getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }
}