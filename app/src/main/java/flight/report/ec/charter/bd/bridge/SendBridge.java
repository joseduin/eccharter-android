package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Send;

public class SendBridge {
    private BaseDatos db;

    public SendBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public Send sendById(int id) {
        return db.send.sendById(id);
    }

    public void sendUpdate(Send send, String atribute) {
        Send aux = sendById(send.getId());
        switch (atribute) {
            case "server":
                aux.setServer( send.isServer() );
                break;
            case "mail":
                aux.setMail( send.isMail() );
                break;
        }
        db.actualizar(convertSend(aux, true), ConstantesBaseDatos.TABLE_SEND, ConstantesBaseDatos.TABLE_SEND_ID, aux.getId());
    }

    public void sendInsert(Send send) {
        db.insertar(convertSend(send, false), ConstantesBaseDatos.TABLE_SEND);
    }

    private ContentValues convertSend(Send send, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_SEND_ID, send.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_SEND_SERVER, ConstantesBaseDatos.intergetConvert( send.isServer() ));
        contentValues.put(ConstantesBaseDatos.TABLE_SEND_MAIL, ConstantesBaseDatos.intergetConvert( send.isMail() ));
        return contentValues;
    }
}
