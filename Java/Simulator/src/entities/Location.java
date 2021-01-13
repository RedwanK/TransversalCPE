package entities;

public class Location {
    private int id;
    private float latitude;
    private float longitude;
    private int cityId;

    public Location() {}

    public Location(int id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(int id, float latitude, float longitude, int cityId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
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
}
