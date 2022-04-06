package sv.edu.udb.dentalife.models;

public class Dentist_Model {
    private String id;
    private String name;
    private String id_specialty;
    private String img;

    public Dentist_Model() {}

    public Dentist_Model(String id, String name, String id_specialty, String img) {
        this.id = id;
        this.name = name;
        this.id_specialty = id_specialty;
        this.img = img;
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

    public String getId_specialty() {
        return id_specialty;
    }

    public void setId_specialty(String id_specialty) {
        this.id_specialty = id_specialty;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}