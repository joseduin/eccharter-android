package flight.report.ec.charter.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IComponent;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Component;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.ComponentResponse;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComponentPresenter implements IComponentPresenter {
    private IComponent iComponent;
    private Context context;
    private BdContructor bd;
    private Connection connection;
    private ArrayList<Component> components = new ArrayList<>();
    private Aircraft aircraftGlobal;
    private ProgressDialog progressDialog;

    public ComponentPresenter(IComponent iComponent, Context context, Aircraft aircraftGlobal, ArrayList<Component> local) {
        this.iComponent = iComponent;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);
        this.aircraftGlobal = aircraftGlobal;


        if (local.isEmpty()) {
            this.getComponents();
        } else {
            this.components = local;
            displayComponents();
        }
    }

    private void searchBd() {
        this.components = bd.component.getComponentByAircraft(aircraftGlobal.getId_web());
        this.displayComponents();
    }

    private void searchServer(final boolean loading) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.aircraftsComponents();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<ComponentResponse> call = endPointsApi.getAircraftComponents(aircraftGlobal.getId_web());
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<ComponentResponse>() {
            @Override
            public void onResponse(Call<ComponentResponse> call, Response<ComponentResponse> response) {
                if (loading)
                    progressDialog.dismiss();
                Log.e("CODE", response.code() + "");

                if (response.code() == 200) {
                    updateList(response.body().getComponents());
                }
            }

            @Override
            public void onFailure(Call<ComponentResponse> call, Throwable t) {
                if (loading)
                    progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    private void updateList(ArrayList<Component> components) {
        bd.component.dataFromServer(components, aircraftGlobal);
        this.components = bd.component.getComponentByAircraft(aircraftGlobal.getId_web());

        listIsEmpty();
        this.iComponent.updateList(this.components);
    }

    private void listIsEmpty() {
        if (this.components.isEmpty())
            Mensaje.mensajeCorto(context, context.getResources().getString(R.string.not_component));
    }

    @Override
    public void getComponents() {
        searchBd();
        if (connection.isOnline()) {
            boolean loading = false;//this.components.isEmpty();
            if (loading) {
                progressDialog = Mensaje.progressConsultar(context);
                progressDialog.show();
            }
            searchServer(loading);
        } else {
            this.listIsEmpty();
        }
    }

    @Override
    public void displayComponents() {
        this.iComponent.setAdapter(this.iComponent.initAdapter(this.components));
        this.iComponent.generateLayout();
    }
}
