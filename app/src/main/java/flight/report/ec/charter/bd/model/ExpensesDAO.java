package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Report;

public class ExpensesDAO {
    private SQLiteOpenHelper db;
    private ReportDAO reportDAO;

    public ExpensesDAO(SQLiteOpenHelper db) {
        this.db = db;
        this.reportDAO = new ReportDAO(db);
    }

    public ArrayList<Expenses> reportExpenses(int id_report) {
        ArrayList<Expenses> reports = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_EXPENSES +
                " WHERE " + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + "=" + id_report;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            reports.add( getExpenses(registros) );
        }

        db.close();
        return reports;
    }

    public Expenses reportExpensesById(int id) {
        Expenses expenses = new Expenses();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_EXPENSES +
                " WHERE " + ConstantesBaseDatos.TABLE_EXPENSES_ID + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            expenses = getExpenses(registros);
        }

        db.close();
        return expenses;
    }

    private Expenses getExpenses(Cursor cursors) {
        Report report = reportDAO.reportById(cursors.getInt(1));
        return Expenses.getExpenses(cursors, report);
    }

}
