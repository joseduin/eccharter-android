package flight.report.ec.charter.interfaz;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerDocumentAdapter;
import flight.report.ec.charter.modelo.components.Document;

public interface IDocument {

    public void generateLayout();

    public RecyclerDocumentAdapter initAdapter(ArrayList<Document> list);

    public void setAdapter(RecyclerDocumentAdapter adapter);

    public void updateList(ArrayList<Document> list);

}
