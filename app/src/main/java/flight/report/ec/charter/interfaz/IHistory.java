package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerHistoryAdapter;
import flight.report.ec.charter.modelo.Report;

public interface IHistory {

    public void generateLayout();

    public RecyclerHistoryAdapter initAdapter(ArrayList<Report> list);

    public void setAdapter(RecyclerHistoryAdapter adapter);

    public void updateList();

}
