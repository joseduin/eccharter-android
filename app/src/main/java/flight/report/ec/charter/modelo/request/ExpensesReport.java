package flight.report.ec.charter.modelo.request;

import flight.report.ec.charter.modelo.Expenses;

public class ExpensesReport {
    private int report;
    private double total;
    private String currency;
    private String description;
    private String photo;
    private int id;

    public ExpensesReport(Expenses e) {
        this.report = e.getReport().getId();
        this.total = e.getTotal();
        this.currency = e.getCurrency();
        this.description = e.getDescription();
        this.photo = e.getPhoto();
        this.id = e.getId();
    }
    public ExpensesReport() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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
}
