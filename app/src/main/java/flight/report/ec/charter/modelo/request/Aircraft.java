package flight.report.ec.charter.modelo.request;

import flight.report.ec.charter.modelo.AircraftReport;

public class Aircraft {

    private int report;
    private String description;
    private String photo;
    private int id;

    public Aircraft(AircraftReport a) {
        this.id = a.getId();
        this.report = a.getReport().getId();
        this.description = a.getDescription();
        this.photo = a.getPhoto();
    }
    public Aircraft() {

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
