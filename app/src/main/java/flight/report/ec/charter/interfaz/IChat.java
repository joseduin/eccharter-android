package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.AdapterMensajes;
import flight.report.ec.charter.modelo.components.Chat;

public interface IChat {

    public void generateLayout();

    public AdapterMensajes initAdapter(ArrayList<Chat> list);

    public void setAdapter(AdapterMensajes adapter);

    public void updateList(ArrayList<Chat> list);

}
