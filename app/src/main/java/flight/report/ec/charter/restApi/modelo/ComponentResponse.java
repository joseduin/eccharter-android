package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.components.Component;

public class ComponentResponse {

    private ArrayList<Component> components = new ArrayList<>();

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }
}
