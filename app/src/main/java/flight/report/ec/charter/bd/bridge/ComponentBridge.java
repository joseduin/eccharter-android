package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Component;
import flight.report.ec.charter.utils.ArrayUtil;

public class ComponentBridge {
    private BaseDatos db;

    public ComponentBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public ArrayList<Component> getComponents() {
        return this.db.component.getComponents();
    }

    public ArrayList<Component> getComponentByAircraft(long aircraftId) {
        return this.db.component.getComponentByAircraft(aircraftId);
    }

    public void componentUpdate(Component component) {
        db.actualizar(convertComponent(component, true), ConstantesBaseDatos.TABLE_COMPONENTS, ConstantesBaseDatos.TABLE_COMPONENTS_ID, component.getId());
    }

    public void componentInsert(Component component) {
        db.insertar(convertComponent(component, false), ConstantesBaseDatos.TABLE_COMPONENTS);
    }

    public void componentDelete(Component component) {
        db.delete(ConstantesBaseDatos.TABLE_COMPONENTS, ConstantesBaseDatos.TABLE_COMPONENTS_ID, component.getId());
    }

    public void componentDeleteAll() {
        db.deleteAll(ConstantesBaseDatos.TABLE_COMPONENTS);
    }

    public void dataFromServer(ArrayList<Component> server, Aircraft aircraft) {
        ArrayList<Component> bdComponents = getComponentByAircraft(aircraft.getId_web());

        // arrServer >= arrLocal
        if (server.size() >= bdComponents.size()) {
            for (int i = 0; i < server.size(); i++) {
                Component bdDocument = (Component) ArrayUtil.canGet(bdComponents, i);
                Component component = server.get(i);
                if (bdDocument != null) {
                    bdDocument.clone(component);
                    componentUpdate(bdDocument);
                } else {
                    componentInsert(component);
                }
            }
        } else {
            // arrServer < arrLocal
            for (int i = 0; i < bdComponents.size(); i++) {
                Component component = (Component) ArrayUtil.canGet(server, i);
                Component bdDocument = bdComponents.get(i);
                if (component != null) {
                    bdDocument.clone(component);
                    componentUpdate(bdDocument);
                } else {
                    componentDelete(bdDocument);
                }
            }
        }
    }


    private ContentValues convertComponent(Component component, boolean id) {
        ContentValues contentValues = new ContentValues();
        if (id)
            contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_ID, component.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_AIRCRAFT_ID, component.getAircraftId());
        contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_DRAWING, component.getDrawing());
        contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_STATUS, component.getStatus());
        contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_ITEM, component.getItem());
        contentValues.put(ConstantesBaseDatos.TABLE_COMPONENTS_POSITION, component.getPosition());

        return contentValues;
    }
}
