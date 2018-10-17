package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Document;
import flight.report.ec.charter.utils.ArrayUtil;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.StringUtil;

public class DocumentBridge {
    private BaseDatos db;

    public DocumentBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Document> getDocuments() {
        return this.db.document.getDocuments();
    }

    public ArrayList<Document> getDocumentsByAircraft(long aircraftId) {
        return this.db.document.getDocumentsByMasterType(3, aircraftId);
    }

    public void documenttUpdate(Document document) {
        db.actualizar(convertDocument(document, true), ConstantesBaseDatos.TABLE_DOCS, ConstantesBaseDatos.TABLE_DOCS_ID, document.getId());
    }

    public void documentInsert(Document document) {
        db.insertar(convertDocument(document, false), ConstantesBaseDatos.TABLE_DOCS);
    }

    public void documentDelete(Document document) {
        db.delete(ConstantesBaseDatos.TABLE_DOCS, ConstantesBaseDatos.TABLE_DOCS_ID, document.getId());
    }

    public Document getDocumentsBySrc(String src) {
        return db.document.getDocumentsBySrc(src);
    }

    public void documentDeleteAll() {
        db.deleteAll(ConstantesBaseDatos.TABLE_DOCS);
    }

    public void dataFromServer(ArrayList<Document> server, Aircraft aircraft) {
        ArrayList<Document> bdDocuments = getDocumentsByAircraft(aircraft.getId_web());

        // arrServer >= arrLocal
        if (server.size() >= bdDocuments.size()) {
            for (int i = 0; i < server.size(); i++) {
                Document bdDocument = (Document) ArrayUtil.canGet(bdDocuments, i);
                Document document = server.get(i);
                if (bdDocument != null) {
                    Document aux = getDocumentsBySrc(document.getSrc());
                    if (aux != null) {
                        bdDocument.clone(document);
                        bdDocument.setSrcImgSave( aux.getSrcImgSave() );
                        bdDocument.setSrcSave( aux.getSrcSave() );
                        documenttUpdate(bdDocument);
                    } else {
                        bdDocument.clone(document);
                        documenttUpdate(bdDocument);
                    }
                } else {
                    documentInsert(document);
                }
            }
        } else {
            // arrServer < arrLocal
            for (int i = 0; i < bdDocuments.size(); i++) {
                Document document = (Document) ArrayUtil.canGet(server, i);
                Document bdDocument = bdDocuments.get(i);
                if (document != null) {
                    Document aux = getDocumentsBySrc(document.getSrc());
                    if (aux != null) {
                        bdDocument.clone(document);
                        bdDocument.setSrcImgSave( aux.getSrcImgSave() );
                        bdDocument.setSrcSave( aux.getSrcSave() );
                        documenttUpdate(bdDocument);
                    } else {
                        bdDocument.clone(document);
                        documenttUpdate(bdDocument);
                    }
                } else {
                    if (bdDocument.getSrcImgSave()==null) {
                        File file = new File(bdDocument.getSrcImgSave());
                        file.delete();
                    }
                    documentDelete(bdDocument);
                }
            }
        }
    }

    private ContentValues convertDocument(Document document, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_DOCS_ID, document.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_MASTER_TYPE, document.getMasterType());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_MASTER_ID, document.getMasterId());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_IS_IMG, ConstantesBaseDatos.intergetConvert(document.getIs_img()));
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_TITLE, document.getTitle());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_SRC, document.getSrc());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_SRC_SAVE, document.getSrcSave());
        contentValues.put(ConstantesBaseDatos.TABLE_DOCS_IMG_SAVE, document.getSrcImgSave());

        return contentValues;
    }
}
