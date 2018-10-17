package flight.report.ec.charter.modelo.event;

public class EventChat {
    public static String chatUpdateList = "chat_update_list";

    private final String message;
    private final String master_type;

    public EventChat(String message, String master_type) {
        this.message = message;
        this.master_type = master_type;
    }

    public String getMessage() {
        return message;
    }

    public String getMaster_type() {
        return master_type;
    }
}
