package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerComboSettingsAdapter;

public interface IComboSetting {

    public void generateLayout();

    public RecyclerComboSettingsAdapter initAdapter(ArrayList<String> list);

    public void setAdapter(RecyclerComboSettingsAdapter adapter);

    public void updateList(ArrayList<String> list);

}
