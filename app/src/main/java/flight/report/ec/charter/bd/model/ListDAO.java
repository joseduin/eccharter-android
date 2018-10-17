package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ListDAO {
    private SQLiteOpenHelper db;

    public ListDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<String> obtenerListado(String listado) {
        ArrayList<String> lista = new ArrayList<>();
        String query = "SELECT * FROM " + listado;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            lista.add( registros.getString(1) );
        }

        db.close();
        return lista;
    }

    public int obtenerPosInListado(String value, String listado) {
        String query = "SELECT * FROM " + listado;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        int pos = 0;
        while (registros.moveToNext()) {
            if ( registros.getString(1).equals(value) ) {
                pos = registros.getInt(0);
                break;
            }
        }

        db.close();
        return pos;
    }
}
