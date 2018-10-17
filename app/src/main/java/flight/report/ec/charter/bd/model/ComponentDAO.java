package flight.report.ec.charter.bd.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Component;

public class ComponentDAO {
    private SQLiteOpenHelper db;

    public ComponentDAO(SQLiteOpenHelper db) {
        this.db = db;
    }

    public ArrayList<Component> getComponents() {
        ArrayList<Component> components = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_COMPONENTS;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            components.add( getComponent(cursors) );
        }

        db.close();
        return components;
    }

    public ArrayList<Component> getComponentByAircraft(Long aircraftId) {
        ArrayList<Component> components = new ArrayList<>();
        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_COMPONENTS +
                " WHERE " + ConstantesBaseDatos.TABLE_COMPONENTS_AIRCRAFT_ID + "=" + aircraftId;
        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursors = db.rawQuery(query, null);

        while (cursors.moveToNext()) {
            components.add( getComponent(cursors) );
        }

        db.close();
        return components;
    }

    private Component getComponent(Cursor cursors) {
        return Component.getComponent(cursors);
    }

}
