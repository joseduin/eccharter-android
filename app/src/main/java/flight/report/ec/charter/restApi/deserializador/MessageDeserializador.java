package flight.report.ec.charter.restApi.deserializador;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import flight.report.ec.charter.modelo.response.Message;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.MessageResponse;

/**
 * Created by Jose on 18/4/2018.
 */

public class MessageDeserializador implements JsonDeserializer<MessageResponse> {
    @Override
    public MessageResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        obj.add(JsonKeys.data, json);

        MessageResponse response = gson.fromJson(obj.toString(), MessageResponse.class);
        JsonObject data = obj.getAsJsonObject(JsonKeys.data);

        response.setMessage(deserializador(data));
        return response;
    }

    private Message deserializador(JsonObject data) {
        return new Message(data.get(JsonKeys.message).getAsString());
    }
}
