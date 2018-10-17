package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Aircraft;

public class AircraftDAO {
    private SQLiteOpenHelper db;

    public AircraftDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<Aircraft> getAircrafts() {
        ArrayList<Aircraft> aircrafts = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_AIRCRAFTS;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            aircrafts.add( getAircraft(cursors) );
        }

        db.close();
        return aircrafts;
    }

    public Aircraft searchById(Long id) {
        Aircraft aircraft = new Aircraft();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_AIRCRAFTS +
                " WHERE " + ConstantesBaseDatos.TABLE_AIRCRAFTS_ID_WEB + "=" + id;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()) {
            aircraft = getAircraft(registros);
        }

        db.close();
        return aircraft;
    }

    private Aircraft getAircraft(Cursor cursors) {
        return Aircraft.getAircrafts(cursors);
    }

}
