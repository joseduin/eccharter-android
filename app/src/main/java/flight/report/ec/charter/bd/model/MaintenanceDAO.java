package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Report;

public class MaintenanceDAO {
    private SQLiteOpenHelper db;
    private ReportDAO reportDAO;

    public MaintenanceDAO(SQLiteOpenHelper db) {
        this.db = db;
        this.reportDAO = new ReportDAO(db);
    }

    public ArrayList<AircraftReport> reportAircraftReport(int id_report) {
        ArrayList<AircraftReport> aircraftReports = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + "=" + id_report;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            aircraftReports.add( getAircraftReport(registros) );
        }

        db.close();
        return aircraftReports;
    }

    public AircraftReport reportAircraftById(int id) {
        AircraftReport aircraftReport = new AircraftReport();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_AIRCRAFT_REPORT_ID + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            aircraftReport = getAircraftReport(registros);
        }

        db.close();
        return aircraftReport;
    }

    private AircraftReport getAircraftReport(Cursor cursors) {
        Report report = reportDAO.reportById(cursors.getInt(1));
        return AircraftReport.getAircraftReport(cursors, report);
    }
}
