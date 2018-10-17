package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Document;

public class DocumentDAO {
    private SQLiteOpenHelper db;

    public DocumentDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<Document> getDocuments() {
        ArrayList<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_DOCS;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            documents.add( getDocument(cursors) );
        }

        db.close();
        return documents;
    }

    public ArrayList<Document> getDocumentsByMasterType(int masterType, Long masterId) {
        ArrayList<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_DOCS +
                " WHERE " + ConstantesBaseDatos.TABLE_DOCS_MASTER_TYPE + "=" + masterType +
                " AND " + ConstantesBaseDatos.TABLE_DOCS_MASTER_ID + "=" + masterId;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            documents.add( getDocument(cursors) );
        }

        db.close();
        return documents;
    }

    public Document getDocumentsBySrc(String src) {
        Document document = null;
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_DOCS +
                " WHERE " + ConstantesBaseDatos.TABLE_DOCS_SRC + "='" + src + "'";
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            document = getDocument(cursors);
        }

        db.close();
        return document;
    }

    private Document getDocument(Cursor cursors) {
        return Document.getDocument(cursors);
    }

}
