package flight.report.ec.charter.modelo.response;

/**
 * Created by Jose on 18/4/2018.
 */

public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public Message() {}

    public String getMgs() {
        return message;
    }

    public void setMgs(String mgs) {
        this.message = mgs;
    }
}
