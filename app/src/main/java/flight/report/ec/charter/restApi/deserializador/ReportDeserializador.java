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

import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.response.Report;
import flight.report.ec.charter.restApi.JsonKeys;
import flight.report.ec.charter.restApi.modelo.ReportResponse;
import flight.report.ec.charter.utils.Hora;

public class ReportDeserializador implements JsonDeserializer<ReportResponse> {
    @Override
    public ReportResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ReportResponse response = gson.fromJson(json.toString(), ReportResponse.class);
        JsonArray data = json.getAsJsonObject().getAsJsonArray(JsonKeys.value);
        response.setReport(deserializador(data));
        return response;
    }

    private ArrayList<Report> deserializador(JsonArray data) {
        ArrayList<Report> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();
            String android = t.get(JsonKeys.app).getAsString();
            boolean app = android.equals("android");

            flight.report.ec.charter.modelo.Report report = new flight.report.ec.charter.modelo.Report();
            report.setCustomer( JsonKeys.valid_str(t.get(JsonKeys.customer)) );
            report.setPassengers( JsonKeys.valid_str(t.get(JsonKeys.passengers)) );
            report.setPassengers_photo( app ? JsonKeys.valid_photo(t.get(JsonKeys.passengers_photo)) : null );
            report.setAircraft( JsonKeys.valid_str(t.get(JsonKeys.aircraft)) );
            report.setCapitan( JsonKeys.valid_str(t.get(JsonKeys.capitan)) );
            report.setCopilot( JsonKeys.valid_str(t.get(JsonKeys.copilot)) );
            try {
                report.setDate( Hora.sdf.format( Hora.server.parse(JsonKeys.valid_str(t.get(JsonKeys.date))) ) );
            } catch (Exception ignored) { }
            report.setRoute( JsonKeys.valid_str(t.get(JsonKeys.route)) );
            report.setRemarks( JsonKeys.valid_str(t.get(JsonKeys.remarks)) );
            report.setComboEngine1( JsonKeys.valid_str(t.get(JsonKeys.comboEngine1)) );
            report.setComboEngine2( JsonKeys.valid_str(t.get(JsonKeys.comboEngine2)) );

            report.setGps_flight_time( JsonKeys.valid_dbl(t.get(JsonKeys.gps_flight_time)) );
            report.setHour_initial( JsonKeys.valid_dbl(t.get(JsonKeys.hour_initial)) );
            report.setHour_final( JsonKeys.valid_dbl(t.get(JsonKeys.hour_final)) );
            report.setLong_time( JsonKeys.valid_dbl(t.get(JsonKeys.long_time)) );
            report.setEngine1( JsonKeys.valid_dbl(t.get(JsonKeys.engine1)) );
            report.setEngine2( JsonKeys.valid_dbl(t.get(JsonKeys.engine2)) );

            report.setCockpit( JsonKeys.valid_bool(t.get(JsonKeys.cockpit)) );
            report.setSentMail( JsonKeys.valid_bool(t.get(JsonKeys.sentMail)) );
            report.setSentMailOpe( JsonKeys.valid_bool(t.get(JsonKeys.sentMailOpe)) );
            report.setSentServer( JsonKeys.valid_bool(t.get(JsonKeys.sentServer)) );

            report.setSend(true);

            ArrayList<AircraftReport> maintenances = deserializarMantenimiento(t.get(JsonKeys.maintenances).getAsJsonArray(), report, app);
            ArrayList<Expenses> expenses = deserializarGasto(t.get(JsonKeys.expenses).getAsJsonArray(), report, app);
            ArrayList<Plan> plans = deserializarPlan(t.get(JsonKeys.plans).getAsJsonArray(), report, app);

            lista.add(new Report(report, maintenances, expenses, plans));
        }
        return lista;
    }

    private ArrayList<Plan> deserializarPlan(JsonArray data, flight.report.ec.charter.modelo.Report report, boolean app) {
        ArrayList<Plan> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            Plan plan = new Plan();
            plan.setReport( report );
            plan.setDescription( JsonKeys.valid_str(t.get(JsonKeys.description)) );
            plan.setPhoto( app ? JsonKeys.valid_photo(t.get(JsonKeys.photo)) : null );

            lista.add(plan);
        }
        return lista;
    }

    private ArrayList<Expenses> deserializarGasto(JsonArray data, flight.report.ec.charter.modelo.Report report, boolean app) {
        ArrayList<Expenses> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            Expenses expense = new Expenses();
            expense.setReport( report );
            expense.setTotal( JsonKeys.valid_dbl(t.get(JsonKeys.total)) );
            expense.setCurrency( JsonKeys.valid_str(t.get(JsonKeys.currency)) );
            expense.setDescription( JsonKeys.valid_str(t.get(JsonKeys.description)) );
            expense.setPhoto( app ? JsonKeys.valid_photo(t.get(JsonKeys.photo)) : null );

            lista.add(expense);
        }
        return lista;
    }

    private ArrayList<AircraftReport> deserializarMantenimiento(JsonArray data, flight.report.ec.charter.modelo.Report report, boolean app) {
        ArrayList<AircraftReport> lista = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            JsonObject t = data.get(i).getAsJsonObject();

            AircraftReport maintenance = new AircraftReport();
            maintenance.setReport( report );
            maintenance.setDescription( JsonKeys.valid_str(t.get(JsonKeys.description)) );
            maintenance.setPhoto( app ? JsonKeys.valid_photo(t.get(JsonKeys.photo)) : null );

            lista.add(maintenance);
        }
        return lista;
    }

}