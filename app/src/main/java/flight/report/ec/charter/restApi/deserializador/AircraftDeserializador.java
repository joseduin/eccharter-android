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

import flight.report.ec.charter.modelo.Aircraft;
import flight.report.ec.charter.modelo.Crew;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.AircraftResponse;
import flight.report.ec.charter.restApi.modelo.CrewResponse;

/**
 * Created by Jose on 18/4/2018.
 */

public class AircraftDeserializador implements JsonDeserializer<AircraftResponse> {
    @Override
    public AircraftResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        obj.add(JsonKeys.data, json);

        AircraftResponse response = gson.fromJson(obj.toString(), AircraftResponse.class);
        JsonArray data = obj.getAsJsonObject().getAsJsonArray(JsonKeys.data);

        response.setAircrafts(deserializador(data));
        return response;
    }

    private ArrayList<Aircraft> deserializador(JsonArray data) {
        ArrayList<Aircraft> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            String tail  = t.get(JsonKeys.tail).getAsString();

            Aircraft turno = new Aircraft(tail);
            lista.add(turno);
        }
        return lista;
    }
}
