package sv.edu.udb.dentalife.models;

public class Specialty_Model {
    private String id;
    private String name;

    public Specialty_Model() {}

    public Specialty_Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
