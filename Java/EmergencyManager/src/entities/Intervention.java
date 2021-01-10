package entities;
import java.util.Date;

public class Intervention{
    private int id;
    private Date created_at;
    private Date updated_at;
    private float coeff;
    private int num_veh;
    private int num_age;
    private Date resolved_at;

    public Intervention() {}

    public Intervention(int id) {
        this.id = id;
    }

    public Intervention(int id, Date created_at, Date updated_at, float coeff, int num_veh, int num_age, Date resolved_at) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.coeff = coeff;
        this.num_veh = num_veh;
        this.num_age = num_age;
        this.resolved_at = resolved_at;
    }

    public int getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
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

    public Date getResolved_at() {
        return resolved_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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

    public void setResolved_at(Date resolved_at) {
        this.resolved_at = resolved_at;
    }
}