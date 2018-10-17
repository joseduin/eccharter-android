package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerAircraftTabAdapter;
import flight.report.ec.charter.modelo.components.Aircraft;

public interface IAircrafts {

    public void generateLayout();

    public RecyclerAircraftTabAdapter initAdapter(ArrayList<Aircraft> list);

    public void setAdapter(RecyclerAircraftTabAdapter adapter);

    public void updateList(ArrayList<Aircraft> list);

}
