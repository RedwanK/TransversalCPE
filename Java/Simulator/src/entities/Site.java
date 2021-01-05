package entities;

public class Site {

    private int id;
    private int cityId;
    private int postalCode;
    private String street;
    private float latitude;
    private float longitude;
    private String phoneNumber;

    public Site() {}

    public Site(int id) {
        this.id = id;
    }

    public Site(int id, int cityId, int postalCode, String street, float latitude, float longitude, String phoneNumber) {
        this.id = id;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
