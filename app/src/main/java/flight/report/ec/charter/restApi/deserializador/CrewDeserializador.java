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

import flight.report.ec.charter.modelo.Crew;
import flight.report.ec.charter.modelo.Customer;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.CrewResponse;
import flight.report.ec.charter.restApi.modelo.CustomerResponse;

/**
 * Created by Jose on 18/4/2018.
 */

public class CrewDeserializador implements JsonDeserializer<CrewResponse> {
    @Override
    public CrewResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        obj.add(JsonKeys.data, json);

        CrewResponse response = gson.fromJson(obj.toString(), CrewResponse.class);
        JsonArray data = obj.getAsJsonObject().getAsJsonArray(JsonKeys.data);

        response.setCrews(deserializador(data));
        return response;
    }

    private ArrayList<Crew> deserializador(JsonArray data) {
        ArrayList<Crew> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            String full_name  = t.get(JsonKeys.full_name).getAsString();

            Crew turno = new Crew(full_name);
            lista.add(turno);
        }
        return lista;
    }
}
