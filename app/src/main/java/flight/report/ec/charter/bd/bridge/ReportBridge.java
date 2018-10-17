package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;

public class ReportBridge {
    private BaseDatos db;
    private ExpenseBridge expenseBridge;
    private MaintenanceBridge maintenanceBridge;
    private PlanBridge planBridge;

    public ReportBridge(Context context) {
        this.db = new BaseDatos(context);
        this.planBridge = new PlanBridge(context);
        this.expenseBridge = new ExpenseBridge(context);
        this.maintenanceBridge = new MaintenanceBridge(context);
    }

    public ArrayList<Report> reportTodos() {
        return db.report.reportTodos();
    }

    public ArrayList<Report> reportEnviados() {
        return db.report.reportEnviados();
    }

    public ArrayList<Report> reportPen() {
        return db.report.reportPen();
    }

    public Report reportById(int id) {
        return db.report.reportById(id);
    }

    public void reportUpdate(Report report, String atribute) {
        Report reportAux = reportById(report.getId());
        switch (atribute) {
            case "remarks":
                reportAux.setRemarks( report.getRemarks() );
                break;
            case "passangers":
                reportAux.setPassengers( report.getPassengers() );
                break;
            case "customer":
                reportAux.setCustomer( report.getCustomer() );
                break;
            case "aircraft":
                reportAux.setAircraft( report.getAircraft() );
                break;
            case "capitan":
                reportAux.setCapitan( report.getCapitan() );
                break;
            case "copilot":
                reportAux.setCopilot( report.getCopilot() );
                break;
            case "route":
                reportAux.setRoute( report.getRoute() );
                break;
            case "hour_initial":
                reportAux.setHour_initial( report.getHour_initial() );
                break;
            case "hour_final":
                reportAux.setHour_final( report.getHour_final() );
                break;
            case "gps_flight_time":
                reportAux.setGps_flight_time( report.getGps_flight_time() );
                break;
            case "log_time":
                reportAux.setLong_time( report.getLong_time() );
                break;
            case "send":
                reportAux.setSend( report.isSend() );
                break;
            case "passengers_photo":
                reportAux.setPassengers_photo( report.getPassengers_photo() );
                break;
            case "date":
                reportAux.setDate( report.getDate() );
                break;
            case "engine1":
                reportAux.setEngine1( report.getEngine1() );
            case "engine2":
                reportAux.setEngine2( report.getEngine2() );
                break;
            case "combo_engine1":
                reportAux.setComboEngine1( report.getComboEngine1() );
                break;
            case "combo_engine2":
                reportAux.setComboEngine2( report.getComboEngine2() );
                break;
            case "cockpit":
                reportAux.setCockpit( report.isCockpit() );
                break;
            case "pen":
                reportAux.setPen( report.isPen() );
                break;
            case "sent_mail":
                reportAux.setSentMail( report.isSentMail() );
                break;
            case "sent_mail_ope":
                reportAux.setSentMailOpe( report.isSentMailOpe() );
                break;
            case "sent_server":
                reportAux.setSentServer( report.isSentServer() );
                break;
        }
        db.actualizar(convertReport(reportAux, true), ConstantesBaseDatos.TABLE_REPORT, ConstantesBaseDatos.TABLE_REPORT_ID, reportAux.getId());
    }

    public void reportInsert(Report report) {
        db.insertar(convertReport(report, false), ConstantesBaseDatos.TABLE_REPORT);
    }

    public void reportDelete(Report report) {
        db.delete(ConstantesBaseDatos.TABLE_REPORT, ConstantesBaseDatos.TABLE_REPORT_ID, report.getId());

        for (Expenses e : expenseBridge.expensestTodos(report.getId()))
            expenseBridge.expensesDelete(e);
        for (AircraftReport m : maintenanceBridge.aircraftTodos(report.getId()))
            maintenanceBridge.aircraftDelete(m);
        for (Plan p : planBridge.plansTodos(report.getId()))
            planBridge.planDelete(p);
    }

    public Report ultimoBorrador() {
        return db.report.ultimoBorrador();
    }

    public Report getLastOne() {
        return db.report.getLastOne();
    }

    private ContentValues convertReport(Report report, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_REPORT_ID, report.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_CUSTOMER, report.getCustomer());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_PASSENGERS, report.getPassengers());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_PASSENGERS_PHOTO, report.getPassengers_photo());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_AIRCRAFT, report.getAircraft());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_CAPITAN, report.getCapitan());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_COPILOT, report.getCopilot());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_DATE, report.getDate());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_ROUTE, report.getRoute());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_GPS_FLIGHT_TIME, report.getGps_flight_time());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_HOUR_INITIAL, report.getHour_initial());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_HOUR_FINAL, report.getHour_final());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_LONG_TIME, report.getLong_time());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_REMARKS, report.getRemarks());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_SEND, ConstantesBaseDatos.intergetConvert( report.isSend() ));
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_ENGINE_1, report.getEngine1());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_ENGINE_2, report.getEngine2());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_COMBO_ENGINE_1, report.getComboEngine1());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_COMBO_ENGINE_2, report.getComboEngine2());
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_COCKPIT,  ConstantesBaseDatos.intergetConvert( report.isCockpit() ));
        contentValues.put(ConstantesBaseDatos.TABLE_REPORT_PEN,  ConstantesBaseDatos.intergetConvert( report.isPen() ));
        contentValues.put(ConstantesBaseDatos.TABLE_SENT_MAIL,  ConstantesBaseDatos.intergetConvert( report.isSentMail() ));
        contentValues.put(ConstantesBaseDatos.TABLE_SENT_SERVER,  ConstantesBaseDatos.intergetConvert( report.isSentServer() ));
        contentValues.put(ConstantesBaseDatos.TABLE_SENT_MAIL_OPE,  ConstantesBaseDatos.intergetConvert( report.isSentMailOpe() ));

        return contentValues;
    }
}
