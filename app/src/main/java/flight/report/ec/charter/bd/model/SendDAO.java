package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Send;

public class SendDAO {
    private SQLiteOpenHelper db;

    public SendDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public Send sendById(int id) {
        Send send = new Send();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_SEND +
                " WHERE " + ConstantesBaseDatos.TABLE_SEND_ID + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            send = getSend(registros);
        }

        db.close();
        return send;
    }

    private Send getSend(Cursor cursors) {
        return Send.getSend(cursors);
    }
}
