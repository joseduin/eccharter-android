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

import flight.report.ec.charter.modelo.components.Component;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.ComponentResponse;
import flight.report.ec.charter.utils.StringUtil;

public class ComponentDeserializador implements JsonDeserializer<ComponentResponse> {
    @Override
    public ComponentResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ComponentResponse response = gson.fromJson(json, ComponentResponse.class);
        JsonArray data = json.getAsJsonObject().getAsJsonArray(JsonKeys.value);

        response.setComponents(deserializer(data));
        return response;
    }

    private ArrayList<Component> deserializer(JsonArray data) {
        ArrayList<Component> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            Long aircraft_id  = t.get(JsonKeys.aircraft_id).getAsLong();
            String drawing  = StringUtil.removeJumps(JsonKeys.valid_str(t.get(JsonKeys.drawing))) +
                                " " +
                                StringUtil.removeJumps(JsonKeys.valid_str(t.get(JsonKeys.task)));
            String itemComponent = JsonKeys.valid_str(t.get(JsonKeys.item));
            String position = JsonKeys.valid_str(t.get(JsonKeys.position));
            int status  = t.get(JsonKeys.status).getAsInt();

            if (status!=1) {
                Component item = new Component(aircraft_id, drawing, itemComponent, position, status);
                lista.add(item); }
        }

        return lista;
    }
}
