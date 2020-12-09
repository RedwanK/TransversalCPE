package entities;

public class Job {
    private int id;
    private String name;
    private int hierarchy;
    private int agentId;

    public Job() {}

    public Job(int id) {
        this.id = id;
    }

    public Job(int id, String name, int hierarchy, int agentId) {
        this.id = id;
        this.name = name;
        this.hierarchy = hierarchy;
        this.agentId = agentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Job() {

    }
}
