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
import android.widget.Button;

import java.util.ArrayList;

import flight.report.ec.charter.PlanActivity;
import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.RecyclerPlanAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Keyboard;

public class Plan extends Fragment implements RecyclerPlanAdapter.CallbackInterface,
        DialogPhoto.CallbackInterfaceDialog {
    private RecyclerView reciclerPlan;

    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    private RecyclerPlanAdapter adaptadorReclicler;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    public static final String REPORTE_ID = "report_id";

    private Keyboard keyboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        this.keyboard = new Keyboard(Plan.this.getContext());
        this.bd = new BdContructor(Plan.this.getContext());

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
        reciclerPlan.setLayoutManager(llm);

        adaptadorReclicler = new RecyclerPlanAdapter(bd.plan.plansTodos(reporteGlobal.getId()), Plan.this);
        reciclerPlan.setAdapter(adaptadorReclicler);
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista(View v) {
        reciclerPlan = v.findViewById(R.id.reciclerPlan);
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
    public void goToPlan(int id) {
        Intent i = IrA.vista(Plan.this.getContext(), PlanActivity.class, id, reporteGlobal.getId());
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
                    switch (data.getStringExtra("RELOAD_PLAN")) {
                        case "YES":
                            ArrayList<flight.report.ec.charter.modelo.Plan> lista = bd.plan.plansTodos(reporteGlobal.getId());
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
                goToPlan(position);
                break;
            case "delete":
                DialogPhoto.alerDeletetInit(this, "ARE YOU SURE?", position,"plan");
                break;
        }
    }

    /**
     * Callback si recargamos la data
     */
    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b)
            adaptadorReclicler.updateList(bd.plan.plansTodos(reporteGlobal.getId()));
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
        keyboard.hideKeyboard(Plan.this.getActivity().getCurrentFocus());
    }
}
