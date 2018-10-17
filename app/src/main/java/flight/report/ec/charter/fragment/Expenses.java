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

import flight.report.ec.charter.R;
import flight.report.ec.charter.Expensesctivity;
import flight.report.ec.charter.adaptador.RecyclerExpensesAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Keyboard;

/**
 * Created by Jose on 19/1/2018.
 */

public class Expenses extends Fragment implements RecyclerExpensesAdapter.CallbackInterface,
    DialogPhoto.CallbackInterfaceDialog {
    private RecyclerView reciclerExpenses;

    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    private RecyclerExpensesAdapter adaptadorReclicler;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    public static final String REPORTE_ID = "report_id";

    private Keyboard keyboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expenses, container, false);
        this.keyboard = new Keyboard(Expenses.this.getContext());
        this.bd = new BdContructor(Expenses.this.getContext());

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(REPORTE_ID);
            reporteGlobal = bd.report.reportById(id);
        }

        enlazarVista(v);
        loadRecicler();

        return v;
    }

    public void setReporteGlobal(flight.report.ec.charter.modelo.Report reporteGlobal) {
        this.reporteGlobal = reporteGlobal;
    }

    private void loadRecicler() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerExpenses.setLayoutManager(llm);

        adaptadorReclicler = new RecyclerExpensesAdapter(bd.expense.expensestTodos(reporteGlobal.getId()), Expenses.this);
        reciclerExpenses.setAdapter(adaptadorReclicler);
    }

    private void enlazarVista(View v) {
        reciclerExpenses = v.findViewById(R.id.reciclerExpenses);
    }

    public void goToExpense(int id) {
        Intent i = IrA.vista(Expenses.this.getContext(), Expensesctivity.class, id, reporteGlobal.getId());
        startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    switch (data.getStringExtra("RELOAD_EXPENSES")) {
                        case "YES":
                            adaptadorReclicler.updateList(bd.expense.expensestTodos(reporteGlobal.getId()));
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onHandleSelection(int position, String metohd) {
        switch (metohd) {
            case "edit":
                goToExpense(position);
                break;
            case "delete":
                DialogPhoto.alerDeletetInit(this, "ARE YOU SURE?", position,"expense");
                break;
        }
    }

    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b)
            adaptadorReclicler.updateList(bd.expense.expensestTodos(reporteGlobal.getId()));
    }

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
        keyboard.hideKeyboard(Expenses.this.getActivity().getCurrentFocus());
    }
}
