package flight.report.ec.charter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import flight.report.ec.charter.AircraftActivity;
import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.RecyclerAircraftAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Keyboard;

/**
 * Created by Jose on 19/1/2018.
 */

public class Report extends Fragment implements RecyclerAircraftAdapter.CallbackInterface,
        DialogPhoto.CallbackInterfaceDialog {
    private RecyclerView reciclerReport;

    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    private RecyclerAircraftAdapter adaptadorReclicler;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    public static final String REPORTE_ID = "report_id";

    private Keyboard keyboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        this.keyboard = new Keyboard(Report.this.getContext());
        this.bd = new BdContructor(Report.this.getContext());

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(REPORTE_ID);
            reporteGlobal = bd.report.reportById(id);
        }

        enlazarVista(v);
        loadRecicler();

        return v;
    }

    /**
     * Cargamos la informacion del reciclerView
     */
    private void loadRecicler() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerReport.setLayoutManager(llm);

        adaptadorReclicler = new RecyclerAircraftAdapter(bd.maintenance.aircraftTodos(reporteGlobal.getId()), Report.this);
        reciclerReport.setAdapter(adaptadorReclicler);
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista(View v) {
        reciclerReport = v.findViewById(R.id.reciclerReport);
    }

    /**
     * Asignamos el reporte, que viene del papa Tab
     */
    public void setReporteGlobal(flight.report.ec.charter.modelo.Report reporteGlobal) {
        this.reporteGlobal = reporteGlobal;
    }

    /**
     * Navegamos hacia la otra pantalla, pasamos como parametro el ID del reporte
     */
    public void goToAircraft(int id) {
        Intent i = IrA.vista(Report.this.getContext(), AircraftActivity.class, id, reporteGlobal.getId());
        startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Callback de la pantalla siguiente, si pudo ingresar data en aquella pantalla,
     * entonces recargar la data aca
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    switch (data.getStringExtra("RELOAD_AIRCRAFT")) {
                        case "YES":
                            ArrayList<AircraftReport> lista = bd.maintenance.aircraftTodos(reporteGlobal.getId());
                            adaptadorReclicler.updateList(lista);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Callback del adaptador, para editar o eliminar un item
     */
    @Override
    public void onHandleSelection(int position, String metohd) {
        switch (metohd) {
            case "edit":
                goToAircraft(position);
                break;
            case "delete":
                DialogPhoto.alerDeletetInit(this, "ARE YOU SURE?", position,"aircraft");
                break;
        }
    }

    /**
     * Callback si recargamos la data
     */
    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b)
            adaptadorReclicler.updateList(bd.maintenance.aircraftTodos(reporteGlobal.getId()));
    }

    /**
     * Guardamos el estado del tlf, para cuando es girado
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (reporteGlobal != null)
            currentState.putInt(REPORTE_ID, reporteGlobal.getId());
    }

    /**
     * Ocultar el teclado a nivel de rootPage
     **/
    public void hideKeyboard(View view) {
        keyboard.hideKeyboard(Report.this.getActivity().getCurrentFocus());
    }
}
