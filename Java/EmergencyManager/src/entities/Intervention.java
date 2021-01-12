package entities;
import java.util.Date;

public class Intervention{

    private float coeff;
    private int num_veh;
    private int num_age;
    private int incid;
    private int teamid;

    public Intervention() {}

    /**
     *
     * @param coeff
     * @param num_veh
     * @param num_age
     * @param incid
     * @param teamid
     */
    public Intervention(float coeff, int num_veh, int num_age,int incid,int teamid) {
        this.coeff = coeff;
        this.num_veh = num_veh;
        this.num_age = num_age;
        this.teamid = teamid;
        this.incid = incid;
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

    public int getTeamid() { return teamid; }

    public int getIncid() { return incid; }

    public void setCoeff(float coeff) { this.coeff = coeff; }

    public void setNum_veh(int num_veh) { this.num_veh = num_veh; }

    public void setNum_age(int num_age) { this.num_age = num_age; }

    public void setIncid(int incid) { this.incid = incid; }

    public void setTeamid(int teamid) { this.teamid = teamid; }

}