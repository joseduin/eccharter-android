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

import flight.report.ec.charter.modelo.Customer;
import flight.report.ec.charter.modelo.response.Message;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.CustomerResponse;

/**
 * Created by Jose on 18/4/2018.
 */

public class CustomerDeserializador implements JsonDeserializer<CustomerResponse> {
    @Override
    public CustomerResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        JsonObject obj = new JsonObject();
        obj.add(JsonKeys.data, json);

        CustomerResponse response = gson.fromJson(obj.toString(), CustomerResponse.class);
        JsonArray data = obj.getAsJsonObject().getAsJsonArray(JsonKeys.data);

        response.setCustomers(deserializador(data));
        return response;
    }

    private ArrayList<Customer> deserializador(JsonArray data) {
        ArrayList<Customer> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            String full_name  = t.get(JsonKeys.full_name).getAsString();

            Customer turno = new Customer(full_name);
            lista.add(turno);
        }
        return lista;
    }
}
