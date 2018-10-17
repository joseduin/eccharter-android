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

import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.AircraftComponentResponse;
import flight.report.ec.charter.utils.StringUtil;

public class AircraftsComponentDeserializador implements JsonDeserializer<AircraftComponentResponse> {
    @Override
    public AircraftComponentResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        AircraftComponentResponse response = gson.fromJson(json, AircraftComponentResponse.class);
        JsonArray data = json.getAsJsonObject().getAsJsonArray(JsonKeys.value);

        response.setAircrafts(deserializer(data));
        return response;
    }

    private ArrayList<Aircraft> deserializer(JsonArray data) {
        ArrayList<Aircraft> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();
            Long id  = t.get(JsonKeys.id).getAsLong();
            String tail  = t.get(JsonKeys.tail).getAsString();
            String brand  = t.get(JsonKeys.brand).getAsString();
            String model  = t.get(JsonKeys.model).getAsString();
            int componentGood  = t.get(JsonKeys.good).getAsInt();
            int componentMedium  = t.get(JsonKeys.medium).getAsInt();
            int componentAlert  = t.get(JsonKeys.bad).getAsInt();

            Aircraft item = new Aircraft(id, tail, brand, model);
            item.setId_web(id);
            item.setComponentGood(componentGood);
            item.setComponentMedium(componentMedium);
            item.setComponentAlert(componentAlert);
            lista.add(item);
        }
        return lista;
    }
}
