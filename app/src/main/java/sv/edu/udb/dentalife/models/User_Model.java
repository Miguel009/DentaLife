package sv.edu.udb.dentalife.models;

public class User_Model {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;

    public User_Model() {}

    public User_Model(String first_name,String last_name, String email, String phone, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getFirst_name() {return first_name;}

    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getLast_name() {return last_name;}

    public void setLast_name(String last_name) {this.last_name = last_name;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}


}
