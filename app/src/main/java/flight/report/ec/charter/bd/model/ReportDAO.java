package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Report;

public class ReportDAO {
    private SQLiteOpenHelper db;

    public ReportDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<Report> reportTodos() {
        ArrayList<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            reports.add( getReport(registros) );
        }

        db.close();
        return reports;
    }

    public ArrayList<Report> reportEnviados() {
        ArrayList<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_REPORT_SEND + "=" + 1;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            reports.add( getReport(registros) );
        }

        db.close();
        return reports;
    }

    public ArrayList<Report> reportPen() {
        ArrayList<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_REPORT_PEN + "=" + 1;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            reports.add( getReport(registros) );
        }

        db.close();
        return reports;
    }

    public Report reportById(int id) {
        Report report = new Report();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_REPORT_ID + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            report = getReport(registros);
        }

        db.close();
        return report;
    }

    public Report ultimoBorrador() {
        Report report = null;
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        while (registros.moveToNext()) {
            report = getReport(registros);
        }

        db.close();
        return report;
    }

    public Report getLastOne() {
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_REPORT;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        registros.moveToLast();
        Report report = getReport(registros);

        db.close();
        return report;
    }

    public Report getReport(Cursor cursors) {
        return Report.getReport(cursors);
    }
}
