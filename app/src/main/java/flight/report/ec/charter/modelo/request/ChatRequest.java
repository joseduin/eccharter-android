package flight.report.ec.charter.modelo.request;

import flight.report.ec.charter.modelo.components.Chat;

public class ChatRequest {
    private String time;
    private String message;
    private String username;
    private int type_message;
    private String source;
    private int master_type;
    private Long master_id;

    public ChatRequest(String time, String message, String username, int type_message, String source64, int master_type, Long master_id) {
        this.time = time;
        this.message = message;
        this.username = username;
        this.type_message = type_message;
        this.source = source64;
        this.master_type = master_type;
        this.master_id = master_id;
    }

    public ChatRequest() {}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType_message() {
        return type_message;
    }

    public void setType_message(int type_message) {
        this.type_message = type_message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source64) {
        this.source = source64;
    }

    public int getMaster_type() {
        return master_type;
    }

    public void setMaster_type(int master_type) {
        this.master_type = master_type;
    }

    public Long getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    public static ChatRequest cloneToChatAircraft(Chat chat) {
        ChatRequest request = new ChatRequest();
        request.setMessage(chat.getMessage());
        request.setType_message(chat.getType());
        request.setMaster_type(3);
        request.setMaster_id(chat.getAircraftId());
        request.setUsername(chat.getUserName());
        request.setTime(String.valueOf(chat.getTime()));
        request.setSource(chat.getSrcSave());
        return request;
    }
}
