package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.response.Report;

public class ReportResponse {

    private ArrayList<Report> report;

    public ArrayList<Report> getReport() {
        return report;
    }

    public void setReport(ArrayList<Report> report) {
        this.report = report;
    }
}
