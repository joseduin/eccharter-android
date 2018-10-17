package flight.report.ec.charter.bd.bridge;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import flight.report.ec.charter.bd.BaseDatos;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.Send;
import flight.report.ec.charter.task.DataBase;

public class ListBridge {
    private BaseDatos db;

    public ListBridge(Context context) {
        this.db = new BaseDatos(context);
    }

    public void actualizarListado(String value, int id, String table) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_LIST_ID, id);
        contentValues.put(ConstantesBaseDatos.TABLE_LIST_DESCRIPTION, value);

        db.actualizar(contentValues, table, ConstantesBaseDatos.TABLE_LIST_ID, id);
    }

    public void borrarListado(int id, String table) {
        db.delete(table, ConstantesBaseDatos.TABLE_LIST_ID, id);
    }

    public int obtenerPosInListado(String value, String lista) {
        return db.list.obtenerPosInListado(value, lista);
    }

    public ArrayList<String> listaCustomer() {
        return sortAlphabetic(db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_CUSTOMER));
    }

    public void listaCustomerInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_CUSTOMER);
    }

    public void eliminarCustomers() {
        db.deleteAll(ConstantesBaseDatos.TABLE_LIST_CUSTOMER);
    }

    public ArrayList<String> listaAircraft() {
        return sortAlphabetic(db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_AIRCRAFT));
    }

    public void listaAircraftInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_AIRCRAFT);
    }

    public void eliminarAircrafts() {
        db.deleteAll(ConstantesBaseDatos.TABLE_LIST_AIRCRAFT);
    }

    public ArrayList<String> listaCapitan() {
        return sortAlphabetic(db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_CAPITAN));
    }

    public void listaCapitanInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_CAPITAN);
    }

    public ArrayList<String> listaCopilot() {
        return sortAlphabetic(db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_CAPITAN));
    }

    public void listaCopilotInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_CAPITAN);
    }

    public void eliminarCrews() {
        db.deleteAll(ConstantesBaseDatos.TABLE_LIST_CAPITAN);
    }

    public void insertCapitanAndPilot(String c) {
        listaCapitanInsert(c);
    }

    public ArrayList<String> listaCurrency() {
        return db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_CURRENCY);
    }

    public void listaCurrencyInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_CURRENCY);
    }

    public ArrayList<String> listaEngine() {
        return db.list.obtenerListado(ConstantesBaseDatos.TABLE_LIST_ENGINE);
    }

    public void listaEngineInsert(String c) {
        db.insertar(convertLista(c), ConstantesBaseDatos.TABLE_LIST_ENGINE);
    }

    private ContentValues convertLista(String c) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_LIST_DESCRIPTION, c);
        return contentValues;
    }

    private ArrayList<String> sortAlphabetic(ArrayList<String> list) {
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    public int listPosition(ArrayList<String> list, String name) {
        int i = 0;
        if (name.equals("")) return i;

        for (String l : list) {
            if (!l.equals(name))
                i++;
            else
                break;
        }
        return i==list.size() ? -1 : i;
    }

}
