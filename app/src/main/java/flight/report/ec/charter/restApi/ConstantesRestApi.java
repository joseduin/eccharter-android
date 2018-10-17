package flight.report.ec.charter.restApi;

/**
 * Created by Jose on 18/4/2018.
 */

public class ConstantesRestApi {

    /**
     * 1. Aircraft Activity: lista de aviones (sincronizado con la web, sino con db)
     * 2. Tabs:
     *  2.1. Documentos (sincronizado desde la web, backgroud guardarlas en la db blob listas para descargar)
     *  2.2. Components (sincronizado desde la web, backgroud guardarlas en la bd)
     *  2.3. Chat (sincronizado desde la web, backgroud guardar ultimos 100 mensajes, paginar desde la web) (mensajes pendiente por enviar)
     *      hora:TimeStamp -> Long
     *      mensaje:String
     *      nombre:string -> Username
     *      type_mensaje: 1. texto 2. imagen
     *      urlFoto:String
     *  2.3.1. Firebase push notifications
     * */

    public static final String API  = "https://ec-charter-joseduin.c9users.io/";    // Desarrollo
    //public static final String API  = "https://eccharter.herokuapp.com/";         // Produccion
    private static final String JSON = ".json?movil=true";

    private static final String REPORT_REQUEST = "send-report";
    public static final String URL_REPORT_REQUEST = API + REPORT_REQUEST + JSON;

    private static final String REPORT_PHOTO_REQUEST =  "set-photos-reports";
    public static final String URL_REPORT_PHOTO_REQUEST = API + REPORT_PHOTO_REQUEST + JSON;

    private static final String CUSTOMER = "customers";
    public static final String URL_CUSTOMER = API + CUSTOMER + JSON;

    private static final String AIRCRAFT = "aircrafts";
    public static final String URL_AIRCRAFT = API + AIRCRAFT + JSON;

    private static final String CREW = "crews";
    public static final String URL_CREW = API + CREW + JSON;

    private static final String HISTORY = "app-recovery-history-of-reports/{username}";
    public static final String URL_HISTORY = API + HISTORY + JSON;

    private static final String AIRCRAFT_COMPONENT = "component-alert";
    public static final String URL_AIRCRAFT_COMPONENT = API + AIRCRAFT_COMPONENT + JSON;

    private static final String AIRCRAFT_DOCUMENTS = "aircraft-documents/{aircraft_id}";
    public static final String URL_AIRCRAFT_DOCUMENTS = API + AIRCRAFT_DOCUMENTS + JSON;

    private static final String AIRCRAFT_COMPONENTS = "aircraft-component/{aircraft_id}";
    public static final String URL_AIRCRAFT_COMPONENTS = API + AIRCRAFT_COMPONENTS + JSON;

    private static final String AIRCRAFT_GET_CHAT = "aircraft-chat/{master_type}/{master_id}";
    public static final String URL_AIRCRAFT_GET_CHAT = API + AIRCRAFT_GET_CHAT + JSON;

    private static final String AIRCRAFT_CHAT = "chats";
    public static final String URL_AIRCRAFT_CHAT = API + AIRCRAFT_CHAT + JSON;

}