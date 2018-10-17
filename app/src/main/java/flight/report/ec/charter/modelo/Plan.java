package flight.report.ec.charter.modelo;

import android.database.Cursor;

/**
 * Created by Jose on 22/1/2018.
 */

public class Plan {
    private int id;
    private Report report;
    private String description;
    private String photo;

    public Plan(int id, Report report, String description, String photo) {
        this.id = id;
        this.report = report;
        this.description = description;
        this.photo = photo;
    }
    public Plan() {

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

    public static Plan getPlan(Cursor cursors, Report report) {
        Plan plan = new Plan();
        plan.setId(cursors.getInt(0));
        plan.setReport( report );
        plan.setDescription(cursors.getString(2));
        plan.setPhoto(cursors.getString(3));

        return plan;
    }
}
