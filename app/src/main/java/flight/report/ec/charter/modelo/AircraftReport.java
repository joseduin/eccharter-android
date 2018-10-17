package flight.report.ec.charter.modelo;

import android.database.Cursor;

/**
 * Created by Jose on 22/1/2018.
 */

public class AircraftReport {

    private int id;
    private Report report;
    private String description;
    private String photo;

    public AircraftReport(int id, Report report, String description, String photo) {
        this.id = id;
        this.report = report;
        this.description = description;
        this.photo = photo;
    }
    public AircraftReport() {

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

    public static AircraftReport getAircraftReport(Cursor cursors, Report report) {
        AircraftReport aircraftReport = new AircraftReport();
        aircraftReport.setId(cursors.getInt(0));
        aircraftReport.setReport(report);
        aircraftReport.setDescription(cursors.getString(2));
        aircraftReport.setPhoto(cursors.getString(3));

        return aircraftReport;
    }

}
