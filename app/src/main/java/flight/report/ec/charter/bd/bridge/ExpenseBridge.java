package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Expenses;

public class ExpenseBridge {
    private BaseDatos db;

    public ExpenseBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Expenses> expensestTodos(int report_id) {
        return db.expenses.reportExpenses(report_id);
    }

    public Expenses expensesById(int id) {
        return db.expenses.reportExpensesById(id);
    }

    public void expensesUpdate(Expenses expenses) {
        db.actualizar(convertExpenses(expenses, true), ConstantesBaseDatos.TABLE_EXPENSES, ConstantesBaseDatos.TABLE_EXPENSES_ID, expenses.getId());
    }

    public void expensesInsert(Expenses expenses) {
        db.insertar(convertExpenses(expenses, false), ConstantesBaseDatos.TABLE_EXPENSES);
    }

    public void expensesDelete(Expenses expenses) {
        db.delete(ConstantesBaseDatos.TABLE_EXPENSES, ConstantesBaseDatos.TABLE_EXPENSES_ID, expenses.getId());
    }

    private ContentValues convertExpenses(Expenses expenses, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_ID, expenses.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_ID_REPORT, expenses.getReport().getId());
        contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_TOTAL, expenses.getTotal());
        contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_CURRENCY, expenses.getCurrency());
        contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_DESCRIPTION, expenses.getDescription());
        contentValues.put(ConstantesBaseDatos.TABLE_EXPENSES_PHOTO, expenses.getPhoto());

        return contentValues;
    }
}
