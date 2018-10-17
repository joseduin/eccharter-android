package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Chat;

public class AircraftChatResponse {

    private ArrayList<Chat> chats = new ArrayList<>();

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }
}
