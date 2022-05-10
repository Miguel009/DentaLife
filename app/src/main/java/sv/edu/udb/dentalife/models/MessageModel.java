package sv.edu.udb.dentalife.models;

public class MessageModel {
    private String name;
    private String message;
    private int type;

    public MessageModel() {}

    public MessageModel(String name, String message, int type) {
        this.name = name;
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String id) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
