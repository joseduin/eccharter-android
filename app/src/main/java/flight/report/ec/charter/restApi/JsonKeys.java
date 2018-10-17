package flight.report.ec.charter.restApi;

import com.google.gson.JsonElement;

/**
 * Created by Jose on 18/4/2018.
 */

public class JsonKeys {

    public static String valid_photo(JsonElement element) {
        return element.toString().contains("null") ? null : element.getAsString();
    }

    public static String valid_str(JsonElement element) {
        return element.toString().contains("null") ? "" : element.getAsString();
    }

    public static Double valid_dbl(JsonElement element) {
        return element.toString().contains("null") ? 0.0 : element.getAsDouble();
    }

    public static Boolean valid_bool(JsonElement element) {
        return !element.toString().contains("null") && element.getAsBoolean();
    }

    public static String data = "data";
    public static String message = "message";
    public static String full_name = "full_name";
    public static String tail = "tail";

    public static String value = "value";
    public static String report = "report_id";
    public static String description = "description";
    public static String photo = "photo_app";
    public static String total = "total";
    public static String currency = "currency";
    public static String customer = "customer";
    public static String passengers = "passengers";
    public static String passengers_photo = "photo_app";
    public static String aircraft = "aircraft";
    public static String capitan = "captain";
    public static String copilot = "copilot";
    public static String date = "register_date";
    public static String route = "route";
    public static String gps_flight_time = "gps_flight_time";
    public static String hour_initial = "hour_initial";
    public static String hour_final = "hour_final";
    public static String long_time = "log_time";
    public static String remarks = "remarks";
    public static String engine1 = "engine1";
    public static String engine2 = "engine2";
    public static String comboEngine1 = "combo_engine1";
    public static String comboEngine2 = "combo_engine2";
    public static String cockpit = "cockpit";
    public static String sentMail = "sent_mail";
    public static String sentMailOpe = "sent_mail_ope";
    public static String sentServer = "sent_server";
    public static String app = "app";
    public static String expenses = "expenses";
    public static String maintenances = "maintenances";
    public static String plans = "plans";

    public static String id = "id";
    public static String brand = "brand";
    public static String model = "model";
    public static String good = "good";
    public static String medium = "medium";
    public static String bad = "bad";

    public static String is_img = "is_img";
    public static String title = "title";
    public static String src = "source";

    public static String aircraft_id = "aircraft_id";
    public static String drawing = "component_drawing";
    public static String task = "component_task";
    public static String status = "status";

    public static String time = "time";
    public static String username = "username";
    public static String type_message = "type_message";
    public static String source = "source";
    public static String master_type = "master_type";
    public static String master_id = "master_id";
    public static String item = "item";
    public static String position = "position";

}
