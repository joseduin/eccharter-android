package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.Crew;
import flight.report.ec.charter.modelo.Customer;

public class CrewResponse {

    private ArrayList<Crew> crews = new ArrayList<>();

    public ArrayList<Crew> getCrews() {
        return crews;
    }

    public void setCrews(ArrayList<Crew> crews) {
        this.crews = crews;
    }
}
