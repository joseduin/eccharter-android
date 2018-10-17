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

import flight.report.ec.charter.modelo.components.Document;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.AircraftDocumentResponse;

public class AircraftsDocumentDeserializador implements JsonDeserializer<AircraftDocumentResponse> {
    @Override
    public AircraftDocumentResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        AircraftDocumentResponse response = gson.fromJson(json, AircraftDocumentResponse.class);
        JsonArray data = json.getAsJsonObject().getAsJsonArray(JsonKeys.value);

        response.setDocument(deserializer(data));
        return response;
    }

    private ArrayList<Document> deserializer(JsonArray data) {
        ArrayList<Document> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            Long masterId  = t.get(JsonKeys.aircraft_id).getAsLong();
            boolean is_img  = t.get(JsonKeys.is_img).getAsBoolean();
            String title  = t.get(JsonKeys.title).getAsString();
            String src  = t.get(JsonKeys.src).getAsString();
            int masterType = 3;

            Document item = new Document(masterType, masterId, is_img, title, src);
            lista.add(item);
        }
        return lista;
    }
}
