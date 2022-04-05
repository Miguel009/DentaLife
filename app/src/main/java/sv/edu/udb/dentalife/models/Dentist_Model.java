package sv.edu.udb.dentalife.models;

public class Dentist_Model {
    private String name;
    private String id_specialty;

    public Dentist_Model() {}

    public Dentist_Model(String name, String id_specialty) {
        this.name = name;
        this.id_specialty = id_specialty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_specialty() {
        return id_specialty;
    }

    public void setId_specialty(String id_specialty) {
        this.id_specialty = id_specialty;
    }
}