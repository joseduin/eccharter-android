package flight.report.ec.charter.aircrafts.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.RecyclerDocumentAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IDocument;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.presenter.DocumentPresenter;
import flight.report.ec.charter.presenter.IDocumentPresenter;
import flight.report.ec.charter.utils.BaseFragment;
import flight.report.ec.charter.utils.StringUtil;

public class Document extends BaseFragment implements IDocument {
    private Aircraft aircraftGlobal;
    private RecyclerView recicler;

    private BdContructor bd;
    private RecyclerDocumentAdapter adapter;
    private IDocumentPresenter iDocumentPresenter;
    public static final String AIRCRAFT_ID = "raircraft_id";
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";

    private Bundle INSTANCESTATE = null;
    private ArrayList<flight.report.ec.charter.modelo.components.Document> documentsInstance = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document, container, false);
        bd = new BdContructor(Document.this.getContext());

        INSTANCESTATE = savedInstanceState;
        enlazarVista(v);
        savedInstance(savedInstanceState);
        CanSavedInstance("recicler");
        iDocumentPresenter = new DocumentPresenter(this, Document.this.getContext(), aircraftGlobal, Document.this.getActivity().getContentResolver(), documentsInstance);

        return v;
    }

    private void savedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Long id = savedInstanceState.getLong(AIRCRAFT_ID);
            aircraftGlobal = bd.aircraft.getAircraftById(id);
        }
    }

    public void CanSavedInstance(String value) {
        Bundle instance = INSTANCESTATE;
        if (instance!=null) {
            if (StringUtil.equals(value, "recicler")) {
                documentsInstance = instance.getParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST);
            } else {
                documentsInstance = new ArrayList<>();
            }
        } else {
            documentsInstance = new ArrayList<>();
        }
    }

    private void enlazarVista(View v) {
        recicler = v.findViewById(R.id.recicler);
    }

    public void setAircraftGlobal(Aircraft aircraft) {
        this.aircraftGlobal = aircraft;
    }

    @Override
    public void generateLayout() {
        int column = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 4;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(column, GridLayoutManager.VERTICAL);
        recicler.setLayoutManager(mLayoutManager);
    }

    @Override
    public RecyclerDocumentAdapter initAdapter(ArrayList<flight.report.ec.charter.modelo.components.Document> list) {
        adapter = new RecyclerDocumentAdapter(Document.this.getContext(), list);
        return adapter;
    }

    @Override
    public void setAdapter(RecyclerDocumentAdapter adapter) {
        recicler.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<flight.report.ec.charter.modelo.components.Document> list) {
        adapter.updateList(list);
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (aircraftGlobal != null && !adapter.getDocuments().isEmpty()) {
            currentState.putLong(AIRCRAFT_ID, aircraftGlobal.getId_web());
            currentState.putParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST, (ArrayList<? extends Parcelable>) adapter.getDocuments());
        }
    }

}
