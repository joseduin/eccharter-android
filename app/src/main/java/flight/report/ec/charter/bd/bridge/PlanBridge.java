package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Plan;

public class PlanBridge {
    private BaseDatos db;

    public PlanBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Plan> plansTodos(int report_id) {
        return db.plan.reportPlans(report_id);
    }

    public Plan planById(int id) {
        return db.plan.reportPlanById(id);
    }

    public void planUpdate(Plan plan) {
        db.actualizar(convertPlan(plan, true), ConstantesBaseDatos.TABLE_PLAN_REPORT, ConstantesBaseDatos.TABLE_PLAN_REPORT_ID, plan.getId());
    }

    public void planInsert(Plan plan) {
        db.insertar(convertPlan(plan, false), ConstantesBaseDatos.TABLE_PLAN_REPORT);
    }

    public void planDelete(Plan plan) {
        db.delete(ConstantesBaseDatos.TABLE_PLAN_REPORT, ConstantesBaseDatos.TABLE_PLAN_REPORT_ID, plan.getId());
    }

    private ContentValues convertPlan(Plan plan, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_PLAN_REPORT_ID, plan.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_PLAN_REPORT_ID_REPORT, plan.getReport().getId());
        contentValues.put(ConstantesBaseDatos.TABLE_PLAN_REPORT_DESCRIPTION, plan.getDescription());
        contentValues.put(ConstantesBaseDatos.TABLE_PLAN_REPORT_PHOTO, plan.getPhoto());

        return contentValues;
    }
}
