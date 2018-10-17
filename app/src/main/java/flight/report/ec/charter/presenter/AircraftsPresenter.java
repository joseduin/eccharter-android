package flight.report.ec.charter.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IAircrafts;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.AircraftComponentResponse;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Mensaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AircraftsPresenter implements IAircraftsPresenter {
    private IAircrafts iAircrafts;
    private Context context;
    private BdContructor bd;
    private Connection connection;
    private ArrayList<Aircraft> aircrafts = new ArrayList<>();

    private ProgressDialog progressDialog;

    public AircraftsPresenter(IAircrafts iAircrafts, Context context, ArrayList<Aircraft> local ) {
        this.iAircrafts = iAircrafts;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);

        if (local.isEmpty()) {
            this.getAircrafts();
        } else {
            this.aircrafts = local;
            displayAircrafts();
        }
    }

    @Override
    public void getAircrafts() {
        searchBd();
        if (connection.isOnline()) {
            boolean loading = this.aircrafts.isEmpty();
            if (loading) {
                progressDialog = Mensaje.progressConsultar(context);
                progressDialog.show();
            }

            searchServer(loading);
        } else {
            this.listIsEmpty();
        }
    }

    private void searchBd() {
        this.aircrafts = bd.aircraft.getAircrafts();
        this.displayAircrafts();
    }

    private void searchServer(final boolean loading) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.aircraftsTab();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<AircraftComponentResponse> call = endPointsApi.getAircraftsTab();
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<AircraftComponentResponse>() {
            @Override
            public void onResponse(Call<AircraftComponentResponse> call, Response<AircraftComponentResponse> response) {
                if (loading)
                    progressDialog.dismiss();
                if (response.code() == 200) {
                    updateList(response.body().getAircrafts());
                }

            }

            @Override
            public void onFailure(Call<AircraftComponentResponse> call, Throwable t) {
                if (loading)
                    progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    private void updateList(ArrayList<Aircraft> aircrafts) {
        bd.aircraft.dataFromServer(aircrafts);
        this.aircrafts = aircrafts;

        listIsEmpty();
        this.iAircrafts.updateList(aircrafts);
    }

    private void listIsEmpty() {
        if (this.aircrafts.isEmpty())
            Mensaje.mensajeCorto(context, context.getResources().getString(R.string.not_aircrafts));
    }

    @Override
    public void displayAircrafts() {
        this.iAircrafts.setAdapter(this.iAircrafts.initAdapter(this.aircrafts));
        this.iAircrafts.generateLayout();
    }
}
