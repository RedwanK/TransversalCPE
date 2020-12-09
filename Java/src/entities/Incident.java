package entities;

public class Incident {
    private int id;
    private float latitude;
    private float longitude;
    private int cityId;
    private int intensity;
    private Date updatedAt;

    public Incident() {}

    public Incident(int id) {
        this.id = id;
    }

    public Incident(int id, float latitude, float longitude, int cityId, int intensity, Date updatedAt) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityId = cityId;
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
