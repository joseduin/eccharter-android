package flight.report.ec.charter.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import flight.report.ec.charter.bd.model.*;

/**
 * Created by Jose on 20/1/2018.
 */

public class BaseDatos extends SQLiteOpenHelper {
    public Chat chat;
    public Expenses expenses;
    public Maintenance maintenance;
    public Report report;
    public Plan plan;
    public Send send;
    public Aircraft aircraft;
    public Document document;
    public Component component;
    public List list;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);

        this.expenses = new Expenses();
        this.maintenance = new Maintenance();
        this.report = new Report();
        this.plan = new Plan();
        this.send = new Send();
        this.aircraft = new Aircraft();
        this.document = new Document();
        this.component = new Component();
        this.chat = new Chat();
        this.list = new List();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableReport = "CREATE TABLE " + ConstantesBaseDatos.TABLE_REPORT + "(" +
                ConstantesBaseDatos.TABLE_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_REPORT_CUSTOMER + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_PASSENGERS + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_PASSENGERS_PHOTO + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_AIRCRAFT + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_CAPITAN + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_COPILOT + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_DATE + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_ROUTE + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_GPS_FLIGHT_TIME + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_HOUR_INITIAL + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_HOUR_FINAL + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_LONG_TIME + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_REMARKS + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_SEND + " INTEGER, " +
                ConstantesBaseDatos.TABLE_REPORT_ENGINE_1 + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_ENGINE_2 + " REAL, " +
                ConstantesBaseDatos.TABLE_REPORT_COMBO_ENGINE_1 + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_COMBO_ENGINE_2 + " TEXT, " +
                ConstantesBaseDatos.TABLE_REPORT_COCKPIT + " INTEGER, " +
                ConstantesBaseDatos.TABLE_REPORT_PEN + " INTEGER, " +
                ConstantesBaseDatos.TABLE_SENT_MAIL + " INTEGER, " +
                ConstantesBaseDatos.TABLE_SENT_SERVER + " INTEGER, " +
                ConstantesBaseDatos.TABLE_SENT_MAIL_OPE + " INTEGER " +
                ")";

        String tableExpenses = "CREATE TABLE " + ConstantesBaseDatos.TABLE_EXPENSES + "(" +
                ConstantesBaseDatos.TABLE_EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + " INTEGER, " +
                ConstantesBaseDatos.TABLE_EXPENSES_TOTAL + " REAL, " +
                ConstantesBaseDatos.TABLE_EXPENSES_CURRENCY + " TEXT, " +
                ConstantesBaseDatos.TABLE_EXPENSES_DESCRIPTION + " TEXT, " +
                ConstantesBaseDatos.TABLE_EXPENSES_PHOTO + " TEXT, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_REPORT + "(" + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + ")" +
                ")";

        String tableAircraft = "CREATE TABLE " + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT + "(" +
                ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID_REPORT + " INTEGER, " +
                ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_DESCRIPTION + " TEXT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_PHOTO + " TEXT, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID_REPORT + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_REPORT + "(" + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + ")" +
                ")";

        String tablePlan = "CREATE TABLE " + ConstantesBaseDatos.TABLE_PLAN_REPORT + "(" +
                ConstantesBaseDatos.TABLE_PLAN_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_PLAN_REPORT_ID_REPORT + " INTEGER, " +
                ConstantesBaseDatos.TABLE_PLAN_REPORT_DESCRIPTION + " TEXT, " +
                ConstantesBaseDatos.TABLE_PLAN_REPORT_PHOTO + " TEXT, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_PLAN_REPORT_ID_REPORT + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_REPORT + "(" + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + ")" +
                ")";

        String tableSend = "CREATE TABLE " + ConstantesBaseDatos.TABLE_SEND + "(" +
                ConstantesBaseDatos.TABLE_SEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_SEND_SERVER + " INTEGER, " +
                ConstantesBaseDatos.TABLE_SEND_MAIL + " INTEGER " +
                ")";

        String tableAircrafts = "CREATE TABLE " + ConstantesBaseDatos.TABLE_AIRCRAFTS + "(" +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_TAIL + " TEXT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_BRAND + " TEXT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_MODEL + " TEXT, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_GOOD + " INTEGER, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_MEDIUM + " INTEGER, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_ALERT + " INTEGER, " +
                ConstantesBaseDatos.TABLE_AIRCRAFTS_ID_WEB + " TEXT " +
                ")";

        String tableDocuments = "CREATE TABLE " + ConstantesBaseDatos.TABLE_DOCS + "(" +
                ConstantesBaseDatos.TABLE_DOCS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_DOCS_MASTER_TYPE + " INTEGER, " +
                ConstantesBaseDatos.TABLE_DOCS_MASTER_ID + " INTEGER, " +
                ConstantesBaseDatos.TABLE_DOCS_IS_IMG + " INTEGER, " +
                ConstantesBaseDatos.TABLE_DOCS_TITLE + " TEXT, " +
                ConstantesBaseDatos.TABLE_DOCS_SRC + " TEXT, " +
                ConstantesBaseDatos.TABLE_DOCS_SRC_SAVE + " TEXT, " +
                ConstantesBaseDatos.TABLE_DOCS_IMG_SAVE + " TEXT " +
                ")";

        String tableComponents = "CREATE TABLE " + ConstantesBaseDatos.TABLE_COMPONENTS + "(" +
                ConstantesBaseDatos.TABLE_COMPONENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_COMPONENTS_AIRCRAFT_ID + " INTEGER, " +
                ConstantesBaseDatos.TABLE_COMPONENTS_DRAWING + " TEXT, " +
                ConstantesBaseDatos.TABLE_COMPONENTS_STATUS + " INTEGER, " +
                ConstantesBaseDatos.TABLE_COMPONENTS_ITEM + " TEXT, " +
                ConstantesBaseDatos.TABLE_COMPONENTS_POSITION + " TEXT, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_COMPONENTS_AIRCRAFT_ID + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_AIRCRAFTS + "(" + ConstantesBaseDatos.TABLE_AIRCRAFTS_ID + ")" +
                ")";

        String tableChats = "CREATE TABLE " + ConstantesBaseDatos.TABLE_CHAT + "(" +
                ConstantesBaseDatos.TABLE_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_CHAT_AIRCRAFT_ID + " INTEGER, " +
                ConstantesBaseDatos.TABLE_CHAT_TIME + " INTEGER, " +
                ConstantesBaseDatos.TABLE_CHAT_MGS + " TEXT, " +
                ConstantesBaseDatos.TABLE_CHAT_USER + " TEXT, " +
                ConstantesBaseDatos.TABLE_CHAT_TYPE + " INTEGER, " +
                ConstantesBaseDatos.TABLE_CHAT_SRC + " TEXT, " +
                ConstantesBaseDatos.TABLE_CHAT_SRC_SAVE + " TEXT, " +
                ConstantesBaseDatos.TABLE_CHAT_SEND + " INTEGER, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_CHAT_AIRCRAFT_ID + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_AIRCRAFTS + "(" + ConstantesBaseDatos.TABLE_AIRCRAFTS_ID + ")" +
                ")";

        String tableListCustomer = createTableList(ConstantesBaseDatos.TABLE_LIST_CUSTOMER);
        String tableListAircraft = createTableList(ConstantesBaseDatos.TABLE_LIST_AIRCRAFT);
        String tableListCapitan = createTableList(ConstantesBaseDatos.TABLE_LIST_CAPITAN);
        String tableListCopilot = createTableList(ConstantesBaseDatos.TABLE_LIST_COPILOT);
        String tableListCurrency = createTableList(ConstantesBaseDatos.TABLE_LIST_CURRENCY);
        String tableListEngine = createTableList(ConstantesBaseDatos.TABLE_LIST_ENGINE);

        db.execSQL(tableSend);
        db.execSQL(tableReport);
        db.execSQL(tableExpenses);
        db.execSQL(tableAircraft);
        db.execSQL(tableListCustomer);
        db.execSQL(tableListAircraft);
        db.execSQL(tableListCapitan);
        db.execSQL(tableListCopilot);
        db.execSQL(tableListCurrency);
        db.execSQL(tableListEngine);
        db.execSQL(tablePlan);
        db.execSQL(tableAircrafts);
        db.execSQL(tableDocuments);
        db.execSQL(tableComponents);
        db.execSQL(tableChats);
    }

    private String createTableList(String table) {
        return "CREATE TABLE " + table + "(" +
                ConstantesBaseDatos.TABLE_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_LIST_DESCRIPTION + " TEXT " +
                ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_REPORT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_EXPENSES + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_PLAN_REPORT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_CUSTOMER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_AIRCRAFT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_CAPITAN + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_COPILOT + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_CURRENCY + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_LIST_ENGINE + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_SEND + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_AIRCRAFTS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_DOCS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_COMPONENTS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + ConstantesBaseDatos.TABLE_CHAT + "'");

        onCreate(db);
    }


    public class List extends ListDAO {
        public List() {
            super(BaseDatos.this);
        }
    }

    public class Expenses extends ExpensesDAO {
        public Expenses() {
            super(BaseDatos.this);
        }
    }
    public class Maintenance extends MaintenanceDAO {
        public Maintenance() {
            super(BaseDatos.this);
        }
    }
    public class Report extends ReportDAO {
        public Report() {
            super(BaseDatos.this);
        }
    }
    public class Plan extends PlanDAO {
        public Plan() {
            super(BaseDatos.this);
        }
    }
    public class Send extends SendDAO {
        public Send() {
            super(BaseDatos.this);
        }
    }
    public class Aircraft extends AircraftDAO {
        public Aircraft() {
            super(BaseDatos.this);
        }
    }
    public class Document extends DocumentDAO {
        public Document() {
            super(BaseDatos.this);
        }
    }
    public class Component extends ComponentDAO {
        public Component() {
            super(BaseDatos.this);
        }
    }
    public class Chat extends ChatDAO {
        public Chat() {
            super(BaseDatos.this);
        }
    }

    public long insertar(ContentValues contentValues, String table) {
        Long id = Long.valueOf(-1);
        SQLiteDatabase db = this.getWritableDatabase();
        id = db.insert(table,null, contentValues);
        db.close();
        return id;
    }

    public void actualizar(ContentValues contentValues, String table, String table_id, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, contentValues, table_id + "=" + id, null);
        db.close();
    }

    public void actualizar(ContentValues contentValues, String table, String table_id, Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, contentValues, table_id + "=" + id, null);
        db.close();
    }

    public void delete(String table, String table_id, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, table_id + "=" + id, null);
        db.close();
    }

    public void delete(String table, String table_id, Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, table_id + "=" + id, null);
        db.close();
    }

    public void deleteAll(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }

}
