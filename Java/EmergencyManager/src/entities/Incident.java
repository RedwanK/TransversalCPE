package entities;

import java.util.Date;

public class Incident implements Comparable<Incident> {
    private int id;
    private float latitude;
    private float longitude;
    private int locId;
    private float intensity;
    private Date updatedAt;

    public Incident() {}

    public Incident(int id) {
        this.id = id;
    }

    public Incident(int id, float latitude, float longitude, int locId, float intensity, Date updatedAt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locId = this.locId;
        this.intensity = intensity;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(Incident inc) {
        return ((Float)this.getIntensity()).compareTo(inc.getIntensity());
    }
}
