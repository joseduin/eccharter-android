package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.AircraftReport;

public class MaintenanceBridge {
    private BaseDatos db;

    public MaintenanceBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<AircraftReport> aircraftTodos(int report_id) {
        return db.maintenance.reportAircraftReport(report_id);
    }

    public AircraftReport aircraftReportById(int id) {
        return db.maintenance.reportAircraftById(id);
    }

    public void aircraftUpdate(AircraftReport aircraftReport) {
        db.actualizar(convertAircraft(aircraftReport, true), ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT, ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID, aircraftReport.getId());
    }

    public void aircraftInsert(AircraftReport aircraftReport) {
        db.insertar(convertAircraft(aircraftReport, false), ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT);
    }

    public void aircraftDelete(AircraftReport aircraftReport) {
        db.delete(ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT, ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID, aircraftReport.getId());
    }

    private ContentValues convertAircraft(AircraftReport aircraftReport, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID, aircraftReport.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID_REPORT, aircraftReport.getReport().getId());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_DESCRIPTION, aircraftReport.getDescription());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_PHOTO, aircraftReport.getPhoto());

        return contentValues;
    }

}
