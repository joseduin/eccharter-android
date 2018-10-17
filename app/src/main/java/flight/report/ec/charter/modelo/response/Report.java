package flight.report.ec.charter.modelo.response;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;

public class Report {
    private flight.report.ec.charter.modelo.Report report;
    private ArrayList<AircraftReport> maintenances;
    private ArrayList<Expenses> expenses;
    private ArrayList<Plan> plans;

    public Report() {}

    public Report(flight.report.ec.charter.modelo.Report report, ArrayList<AircraftReport> maintenances, ArrayList<Expenses> expenses, ArrayList<Plan> plans) {
        this.report = report;
        this.maintenances = maintenances;
        this.expenses = expenses;
        this.plans = plans;
    }

    public flight.report.ec.charter.modelo.Report getReport() {
        return report;
    }

    public void setReport(flight.report.ec.charter.modelo.Report report) {
        this.report = report;
    }

    public ArrayList<AircraftReport> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(ArrayList<AircraftReport> maintenances) {
        this.maintenances = maintenances;
    }

    public ArrayList<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expenses> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Plan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<Plan> plans) {
        this.plans = plans;
    }

}
