package entities;

public class Sensor {

    private int id;
    private String name;
    private String reference;
    private String type;
    private int locationId;


    public Sensor() {}

    public Sensor(int id, String name, String reference, String type) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.type = type;
    }

    public Sensor(int id) {
        this.id = id;
    }

    public Sensor(String name, String reference, String type) {
        this.name = name;
        this.reference = reference;
        this.type = type;
    }

    public Sensor(int id, String name, String reference, String type, int locationId) {
        this.id = id;
        this.name = name;
        this.reference = reference;
        this.type = type;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
