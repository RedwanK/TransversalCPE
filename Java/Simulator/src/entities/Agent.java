package entities;

public class Agent {
    private int id;
    private String firstname;
    private String lastname;
    private String job;
    private int siteId;

    public Agent() {}

    public Agent(int id) {
        this.id = id;
    }

    public Agent(int id, String firstname, String lastname, String job, int siteId) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.siteId = siteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
