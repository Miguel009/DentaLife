package sv.edu.udb.dentalife.models;

public class AppointmentModel {
    private String id;
    private String day;
    private String hour;
    private String comment;
    private String doctor_id;

    public AppointmentModel() {}

    public AppointmentModel(String id, String day, String hour, String comment, String doctor_id) {
        this.id = id;
        this.day = day;
        this.hour = hour;
        this.comment = comment;
        this.doctor_id = doctor_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }
}
