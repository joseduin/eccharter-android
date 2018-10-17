package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.components.Aircraft;

public class AircraftComponentResponse {

    private ArrayList<Aircraft> aircrafts = new ArrayList<>();

    public ArrayList<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(ArrayList<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }
}
