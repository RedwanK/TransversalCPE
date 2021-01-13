package entities;

public class Incident {
    private int id;
    private String type = "fire";
    private String codeIncident;
    private float latitude;
    private float longitude;
    private int locationId;
    private float intensity;
    private int sensorId;
    private boolean resolved;

    public Incident() {}

    public Incident(int id) {
        this.id = id;
        this.resolved = false;
    }

    /**
     *
     * @param id
     * @param type
     * @param codeIncident
     * @param locationId
     * @param intensity
     */
    public Incident(int id, String type, String codeIncident, int locationId, float intensity, float lat, float lon) {
        this.id = id;
        this.type = type;
        this.locationId = locationId;
        this.intensity = intensity;
        this.latitude = lat;
        this.longitude = lon;
        this.codeIncident = codeIncident;
        this.resolved = false;
    }
    public Incident(int id, String type, String codeIncident, int locationId, int sensorId, float intensity) {
        this.id = id;
        this.type = type;
        this.locationId = locationId;
        this.intensity = intensity;
        this.sensorId = sensorId;
        this.codeIncident = codeIncident;
        this.resolved = false;
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

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
        setCodeIncident(intensity);
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeIncident() {
        return codeIncident;
    }

    public void setCodeIncident(String codeIncident) {
        this.codeIncident = codeIncident;
    }

    public void setCodeIncident(float lat, float lon, float intensity) {
        this.latitude = lat;
        this.longitude = lon;
        this.codeIncident = "x:"+lat+";y:"+lon+";v:"+intensity+"#";
    }

    public void setCodeIncident(float intensity) {
        this.codeIncident = "x:"+this.latitude+";y:"+this.longitude+";v:"+this.intensity+"#";
    }
    public void setCodeIncident(float lat, float lon) {
        this.latitude = lat;
        this.longitude = lon;
        this.codeIncident = "x:"+lat+";y:"+lon+";v:"+this.intensity+"#";
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\t\"codeIncident\": \""+this.codeIncident+"\",\n"+
                "\t\"type\": \""+this.type+"\",\n"+
                "\t\"location\": "+ this.locationId + ",\n" +
                "\t\"intensity\": "+this.intensity + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        Incident inc = (Incident) obj;
        return this.id == inc.getId();
    }
}
