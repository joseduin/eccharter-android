package flight.report.ec.charter.modelo.request;

import flight.report.ec.charter.modelo.Plan;

/**
 * Created by Jose on 18/4/2018.
 */

public class PlanRequest {

    private int report;
    private String description;
    private String photo;
    private int id;

    public PlanRequest(Plan a) {
        this.id = a.getId();
        this.report = a.getReport().getId();
        this.description = a.getDescription();
        this.photo = a.getPhoto();
    }

    public PlanRequest() {

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

