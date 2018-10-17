package flight.report.ec.charter.bd;

/**
 * Created by Jose on 27/12/2017.
 */

public final class ConstantesBaseDatos {

    public final static String DATABASE_NAME = "ec_charter";
    public final static int DATABASE_VERSION = 16;

    /* Reporte */
    public final static String TABLE_REPORT                     = "report";
    public final static String TABLE_REPORT_ID                  = "id";
    public final static String TABLE_REPORT_CUSTOMER            = "customer";
    public final static String TABLE_REPORT_PASSENGERS          = "passengers";
    public final static String TABLE_REPORT_PASSENGERS_PHOTO    = "passengers_photo";
    public final static String TABLE_REPORT_AIRCRAFT            = "aircraft";
    public final static String TABLE_REPORT_CAPITAN             = "capitan";
    public final static String TABLE_REPORT_COPILOT             = "copilot";
    public final static String TABLE_REPORT_DATE                = "date";
    public final static String TABLE_REPORT_ROUTE               = "route";
    public final static String TABLE_REPORT_GPS_FLIGHT_TIME     = "gps_flight_time";
    public final static String TABLE_REPORT_HOUR_INITIAL        = "hour_initial";
    public final static String TABLE_REPORT_HOUR_FINAL          = "hour_final";
    public final static String TABLE_REPORT_LONG_TIME           = "long_time";
    public final static String TABLE_REPORT_REMARKS             = "remarks";
    public final static String TABLE_REPORT_SEND                = "send";
    public final static String TABLE_REPORT_ENGINE_1            = "engine_1";
    public final static String TABLE_REPORT_ENGINE_2            = "engine_2";
    public final static String TABLE_REPORT_COMBO_ENGINE_1      = "combo_engine_1";
    public final static String TABLE_REPORT_COMBO_ENGINE_2      = "combo_engine_2";
    public final static String TABLE_REPORT_COCKPIT             = "cockpit";
    public final static String TABLE_REPORT_PEN                 = "pen";
    public final static String TABLE_SENT_MAIL                  = "sent_mail";
    public final static String TABLE_SENT_MAIL_OPE              = "sent_mail_ope";
    public final static String TABLE_SENT_SERVER                = "sent_server";

    /* Expenses */
    public final static String TABLE_EXPENSES                   = "expenses";
    public final static String TABLE_EXPENSES_ID                = "id";
    public final static String TABLE_EXPENSES_ID_REPORT         = "id_report";
    public final static String TABLE_EXPENSES_TOTAL             = "total";
    public final static String TABLE_EXPENSES_CURRENCY          = "currency";
    public final static String TABLE_EXPENSES_DESCRIPTION       = "description";
    public final static String TABLE_EXPENSES_PHOTO             = "photo";

    /* Maintenance */
    public final static String TABLE_AIRCRAFT_REPORT            = "aircraft_report";
    public final static String TABLE_AIRCRAFT_REPORT_ID         = "id";
    public final static String TABLE_AIRCRAFT_REPORT_ID_REPORT  = "id_report";
    public final static String TABLE_AIRCRAFT_REPORT_DESCRIPTION= "description";
    public final static String TABLE_AIRCRAFT_REPORT_PHOTO      = "photo";

    /* Plan */
    public final static String TABLE_PLAN_REPORT                = "plan_report";
    public final static String TABLE_PLAN_REPORT_ID             = "id";
    public final static String TABLE_PLAN_REPORT_ID_REPORT      = "id_report";
    public final static String TABLE_PLAN_REPORT_DESCRIPTION    = "description";
    public final static String TABLE_PLAN_REPORT_PHOTO          = "photo";

    /* ComboBox / Spinner */
    public final static String TABLE_LIST_CUSTOMER              = "list_customer";
    public final static String TABLE_LIST_AIRCRAFT              = "list_aircraft";
    public final static String TABLE_LIST_CAPITAN               = "list_capitan";
    public final static String TABLE_LIST_COPILOT               = "list_copilot";
    public final static String TABLE_LIST_CURRENCY              = "list_currency";
    public final static String TABLE_LIST_ENGINE                = "list_engine";

    public final static String TABLE_LIST_ID                    = "id";
    public final static String TABLE_LIST_DESCRIPTION           = "description";

    /* Medios de envios del reporte: [Mail, Server] */
    public final static String TABLE_SEND                       = "send_report";
    public final static String TABLE_SEND_ID                    = "send_report_id";
    public final static String TABLE_SEND_SERVER                = "send_report_server";
    public final static String TABLE_SEND_MAIL                  = "send_report_mail";

    /* Aircrafts Server */
    public final static String TABLE_AIRCRAFTS = "aircrafts";
    public final static String TABLE_AIRCRAFTS_ID = "id";
    public final static String TABLE_AIRCRAFTS_ID_WEB = "id_web";
    public final static String TABLE_AIRCRAFTS_TAIL = "tail";
    public final static String TABLE_AIRCRAFTS_BRAND = "brand";
    public final static String TABLE_AIRCRAFTS_MODEL = "model";
    public final static String TABLE_AIRCRAFTS_COMPONENT_GOOD = "component_good";
    public final static String TABLE_AIRCRAFTS_COMPONENT_MEDIUM = "component_medium";
    public final static String TABLE_AIRCRAFTS_COMPONENT_ALERT = "component_alert";

    /* Aircrafts Server
    *   MasterType:
    *       1: Crew
    *       2: Customer
    *       3: Aircraft
    * */
    public final static String TABLE_DOCS = "documents";
    public final static String TABLE_DOCS_ID = "id";
    public final static String TABLE_DOCS_MASTER_TYPE = "master_type";
    public final static String TABLE_DOCS_MASTER_ID = "master_id";
    public final static String TABLE_DOCS_IS_IMG = "is_img";
    public final static String TABLE_DOCS_TITLE = "title";
    public final static String TABLE_DOCS_SRC = "source";
    public final static String TABLE_DOCS_SRC_SAVE = "source_saved";
    public final static String TABLE_DOCS_IMG_SAVE = "image_saved";

    /* Aircrafts Components */
    public final static String TABLE_COMPONENTS = "components";
    public final static String TABLE_COMPONENTS_ID = "id";
    public final static String TABLE_COMPONENTS_AIRCRAFT_ID = "aircraft_id";
    public final static String TABLE_COMPONENTS_DRAWING = "drawing";
    public final static String TABLE_COMPONENTS_ITEM = "item";
    public final static String TABLE_COMPONENTS_POSITION = "position";
    public final static String TABLE_COMPONENTS_STATUS = "status";

    /* Chat */
    public final static String TABLE_CHAT = "chats";
    public final static String TABLE_CHAT_ID = "id";
    public final static String TABLE_CHAT_AIRCRAFT_ID = "aircraft_id";
    public final static String TABLE_CHAT_TIME = "timestamp";
    public final static String TABLE_CHAT_MGS = "message";
    public final static String TABLE_CHAT_USER = "username";
    public final static String TABLE_CHAT_TYPE = "chat_type";
    public final static String TABLE_CHAT_SRC = "url_source";
    public final static String TABLE_CHAT_SRC_SAVE = "source_saved";
    public final static String TABLE_CHAT_SEND = "send";

    public static boolean booleanConvert(int b) {
        return b != 0;
    }
    public static int intergetConvert(boolean b) {
        return b ? 1 : 0;
    }

}
