package flight.report.ec.charter.aircrafts.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.RecyclerComponentsAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IComponent;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.presenter.ComponentPresenter;
import flight.report.ec.charter.presenter.IComponentPresenter;
import flight.report.ec.charter.utils.BaseFragment;
import flight.report.ec.charter.utils.StringUtil;

public class Component extends BaseFragment implements IComponent {
    private Aircraft aircraftGlobal;
    private RecyclerView recicler;

    private BdContructor bd;
    private RecyclerComponentsAdapter adapter;
    private IComponentPresenter iComponentPresenter;
    public static final String AIRCRAFT_ID = "raircraft_id";
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";

    private Bundle INSTANCESTATE = null;
    private ArrayList<flight.report.ec.charter.modelo.components.Component> componentsInstance = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_document, container, false);
        bd = new BdContructor(Component.this.getContext());

        INSTANCESTATE = savedInstanceState;
        enlazarVista(v);
        savedInstance(savedInstanceState);
        CanSavedInstance("recicler");
        iComponentPresenter = new ComponentPresenter(this, Component.this.getContext(), aircraftGlobal, componentsInstance);

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
                componentsInstance = instance.getParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST);
            }
        } else {
            componentsInstance = new ArrayList<>();
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
        LinearLayoutManager llm = new LinearLayoutManager(Component.this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recicler.setLayoutManager(llm);
    }

    @Override
    public RecyclerComponentsAdapter initAdapter(ArrayList<flight.report.ec.charter.modelo.components.Component> list) {
        adapter = new RecyclerComponentsAdapter(Component.this.getContext(), list);
        return adapter;
    }

    @Override
    public void setAdapter(RecyclerComponentsAdapter adapter) {
        recicler.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<flight.report.ec.charter.modelo.components.Component> list) {
        adapter.updateList(list);
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST, adapter.getList());

        if (aircraftGlobal != null)
            currentState.putLong(AIRCRAFT_ID, aircraftGlobal.getId_web());
    }
}
