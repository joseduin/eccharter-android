package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.utils.ArrayUtil;

public class AircraftBridge {
    private BaseDatos db;

    public AircraftBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Aircraft> getAircrafts() {
        return this.db.aircraft.getAircrafts();
    }

    public Aircraft getAircraftById(Long webId) {
        return this.db.aircraft.searchById(webId);
    }

    public void aircraftUpdate(Aircraft aircraft) {
        db.actualizar(convertAircrafts(aircraft, true), ConstantesBaseDatos.TABLE_AIRCRAFTS, ConstantesBaseDatos.TABLE_AIRCRAFTS_ID, aircraft.getId());
    }

    public void aircraftInsert(Aircraft aircraft) {
        db.insertar(convertAircrafts(aircraft, false), ConstantesBaseDatos.TABLE_AIRCRAFTS);
    }

    public void aircraftDelete(Aircraft aircraft) {
        db.delete(ConstantesBaseDatos.TABLE_AIRCRAFTS, ConstantesBaseDatos.TABLE_AIRCRAFTS_ID, aircraft.getId());
    }

    public void aircraftDeleteAll() {
        db.deleteAll(ConstantesBaseDatos.TABLE_AIRCRAFTS);
    }

    /**
     * Sobreescribimos la data existente en la bd reusando los IDS existentes,
     * aprovechando la data vieja para actualizarl por la nueva,
     * esto evitando borrar y ingresar cada vez que se busca en el servidor.
     * @param server: (array) data que viene del servidor
     **/
    public void dataFromServer(ArrayList<Aircraft> server) {
        ArrayList<Aircraft> bdAircrafts = getAircrafts();
        // arrServer >= arrLocal
        if (server.size() >= bdAircrafts.size()) {
            for (int i = 0; i < server.size(); i++) {
                Aircraft bdAircraft = (Aircraft) ArrayUtil.canGet(bdAircrafts, i);
                if (bdAircraft != null) {
                    bdAircraft.clone(server.get(i));
                    aircraftUpdate(bdAircraft);
                } else {
                    aircraftInsert(server.get(i));
                }
            }
        } else {
            // arrServer < arrLocal
            for (int i = 0; i < bdAircrafts.size(); i++) {
                Aircraft aircraft = (Aircraft) ArrayUtil.canGet(server, i);
                if (aircraft != null) {
                    Aircraft bdAircraft = bdAircrafts.get(i);
                    bdAircraft.clone(aircraft);
                    aircraftUpdate(bdAircraft);
                } else {
                    aircraftDelete(bdAircrafts.get(i));
                }
            }
        }
    }

    private ContentValues convertAircrafts(Aircraft aircraft, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_ID, aircraft.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_TAIL, aircraft.getTail());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_BRAND, aircraft.getBrand());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_MODEL, aircraft.getModel());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_GOOD, aircraft.getComponentGood());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_MEDIUM, aircraft.getComponentMedium());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_COMPONENT_ALERT, aircraft.getComponentAlert());
        contentValues.put(ConstantesBaseDatos.TABLE_AIRCRAFTS_ID_WEB, String.valueOf(aircraft.getId_web()));

        return contentValues;
    }
}
