package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;

public class PlanDAO {
    private SQLiteOpenHelper db;
    private ReportDAO reportDAO;

    public PlanDAO(SQLiteOpenHelper db) {
        this.db = db;
        this.reportDAO = new ReportDAO(db);
    }

    public ArrayList<Plan> reportPlans(int id_report) {
        ArrayList<Plan> plans = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_PLAN_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT + "=" + id_report;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            plans.add( getPlan(registros) );
        }

        db.close();
        return plans;
    }

    public Plan reportPlanById(int id) {
        Plan plan = new Plan();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_PLAN_REPORT +
                " WHERE " + ConstantesBaseDatos.TABLE_PLAN_REPORT_ID + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            plan = getPlan(registros);
        }

        db.close();
        return plan;
    }

    private Plan getPlan(Cursor cursors) {
        Report report = reportDAO.reportById( cursors.getInt(1) );
        return Plan.getPlan(cursors, report);
    }
}
