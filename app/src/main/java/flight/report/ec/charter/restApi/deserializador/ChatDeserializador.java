package flight.report.ec.charter.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

import flight.report.ec.charter.modelo.components.Chat;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.AircraftChatResponse;
import flight.report.ec.charter.utils.StringUtil;

public class ChatDeserializador implements JsonDeserializer<AircraftChatResponse> {
    @Override
    public AircraftChatResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        AircraftChatResponse response = gson.fromJson(json, AircraftChatResponse.class);
        JsonArray data = json.getAsJsonObject().getAsJsonArray(JsonKeys.value);

        response.setChats(deserializer(data));
        return response;
    }

    private ArrayList<Chat> deserializer(JsonArray data) {
        ArrayList<Chat> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            Long id  = t.get(JsonKeys.id).getAsLong();
            Long time  = Long.parseLong(t.get(JsonKeys.time).getAsString());
            String message  = t.get(JsonKeys.message).getAsString();
            String username  = t.get(JsonKeys.username).getAsString();
            int type_message  = t.get(JsonKeys.type_message).getAsInt();
            String source  = JsonKeys.valid_str(t.get(JsonKeys.source));
            Long master_id  = t.get(JsonKeys.master_id).getAsLong();
            Chat item = new Chat(id, master_id, time, message, username, type_message, source);
            item.setSend(true);

            lista.add(item);
        }
        return lista;
    }

}
