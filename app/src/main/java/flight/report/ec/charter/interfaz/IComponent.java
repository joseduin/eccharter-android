package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerComponentsAdapter;
import flight.report.ec.charter.adaptador.RecyclerDocumentAdapter;
import flight.report.ec.charter.modelo.components.Component;
import flight.report.ec.charter.modelo.components.Document;

public interface IComponent {

    public void generateLayout();

    public RecyclerComponentsAdapter initAdapter(ArrayList<Component> list);

    public void setAdapter(RecyclerComponentsAdapter adapter);

    public void updateList(ArrayList<Component> list);

}
