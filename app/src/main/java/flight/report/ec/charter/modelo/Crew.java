package flight.report.ec.charter.modelo;

public class Crew {

    private String full_name;

    public Crew(String full_name) {
        this.full_name = full_name;
    }

    public Crew() {}

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
