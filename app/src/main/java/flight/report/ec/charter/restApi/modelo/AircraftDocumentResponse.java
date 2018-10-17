package flight.report.ec.charter.restApi.modelo;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Document;

public class AircraftDocumentResponse {

    private ArrayList<Document> documents = new ArrayList<>();

    public ArrayList<Document> getDocument() {
        return documents;
    }

    public void setDocument(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
