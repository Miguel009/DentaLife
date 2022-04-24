package sv.edu.udb.dentalife.models;

public class NewAppointmentModel {
    private String day;
    private String hour;
    private String comment;
    private String dentist_id;

    public NewAppointmentModel() {}

    public NewAppointmentModel(String day, String hour, String comment, String dentist_id) {
        this.day = day;
        this.hour = hour;
        this.comment = comment;
        this.dentist_id = dentist_id;
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

    public String getDentist_id() {
        return dentist_id;
    }

    public void setDentist_id(String dentist_id) {
        this.dentist_id = dentist_id;
    }
}
