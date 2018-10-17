package flight.report.ec.charter.modelo;

import android.database.Cursor;

/**
 * Created by Jose on 22/1/2018.
 */

public class Expenses {

    private int id;
    private Report report;
    private double total;
    private String currency = "$";
    private String description;
    private String photo;

    public Expenses(int id, Report report, double total, String description, String photo) {
        this.id = id;
        this.report = report;
        this.total = total;
        this.description = description;
        this.photo = photo;
    }
    public Expenses() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) { }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static Expenses getExpenses(Cursor cursors, Report report) {
        Expenses expenses = new Expenses();
        expenses.setId(cursors.getInt(0));
        expenses.setReport( report );
        expenses.setTotal(cursors.getDouble(2));
        expenses.setCurrency(cursors.getString(3));
        expenses.setDescription(cursors.getString(4));
        expenses.setPhoto(cursors.getString(5));

        return expenses;
    }
}
