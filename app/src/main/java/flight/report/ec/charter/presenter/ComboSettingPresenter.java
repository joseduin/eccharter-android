package flight.report.ec.charter.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IComboSetting;
import flight.report.ec.charter.modelo.Crew;
import flight.report.ec.charter.modelo.Customer;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.AircraftResponse;
import flight.report.ec.charter.restApi.modelo.CrewResponse;
import flight.report.ec.charter.restApi.modelo.CustomerResponse;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Mensaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComboSettingPresenter implements IComboSettingPresenter {
    private IComboSetting iComboSetting;
    private Context context;
    private BdContructor bd;
    private Connection connection;
    private ArrayList<String> strings = new ArrayList<>();
    private String method;
    private String comboName;
    private int LIST;

    private ProgressDialog progressDialog;

    public ComboSettingPresenter(IComboSetting iComboSetting, Context context, String method, ArrayList<String> local, int LIST, String comboName) {
        this.iComboSetting = iComboSetting;
        this.context = context;
        this.bd = new BdContructor(context);
        this.connection = new Connection(context);
        this.method = method;
        this.comboName = comboName;
        this.LIST = LIST;

        if (local.isEmpty()) {
            this.getList();
        } else {
            this.strings = local;
            getList();
        }
    }

    private void searchBd() {
        ArrayList<String> aux = LIST == 1 ? bd.list.listaCustomer() : LIST == 2 ? bd.list.listaAircraft() : bd.list.listaCapitan();
        strings = new ArrayList<>();
        for (String r : aux) {
            if (!r.equals("-----------------"))
                strings.add(r);
        }
        this.displayList();
    }

    private void updateList(ArrayList<String> strings) {
        this.strings = strings;
        this.iComboSetting.updateList(strings);
        listIsEmpty();
    }

    private void listIsEmpty() {
        String message =  (LIST==1) ? context.getResources().getString(R.string.not_customers) :
                          (LIST==2) ? context.getResources().getString(R.string.not_aircrafts) :
                                      context.getResources().getString(R.string.not_crews);
        if (this.strings.isEmpty())
            Mensaje.mensajeCorto(context, message);
    }

    @Override
    public void getList() {
        if(method==context.getResources().getString(R.string.presenter_local)) {
            searchBd();
            listIsEmpty();
        } else {
            progressDialog = Mensaje.progressConsultar(context);
            progressDialog.show();

            if (LIST==1) {
                getCustomers();
            } else if (LIST==2) {
                getAircrafts();
            } else {
                getCrews();
            }
        }
    }

    /**
     * Obtenemos todos los Clientes del servidor
     */
    private void getCustomers() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.customer();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<CustomerResponse> call = endPointsApi.getCustomers();
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    CustomerResponse resp = response.body();

                    bd.list.eliminarCustomers();
                    bd.list.listaCustomerInsert("-----------------");
                    for (Customer c : resp.getCustomers()) {
                        bd.list.listaCustomerInsert(c.getFull_name());
                    }
                    updateList(bd.list.listaCustomer());
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    /**
     * Obtenemos todos los Aviones del servidor
     */
    private void getAircrafts() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.aircraft();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<AircraftResponse> call = endPointsApi.getAircrafts();
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<AircraftResponse>() {
            @Override
            public void onResponse(Call<AircraftResponse> call, Response<AircraftResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    AircraftResponse resp = response.body();

                    bd.list.eliminarAircrafts();
                    bd.list.listaAircraftInsert("-----------------");
                    for (flight.report.ec.charter.modelo.Aircraft a : resp.getAircrafts()) {
                        bd.list.listaAircraftInsert(a.getTail());
                    }
                    updateList(bd.list.listaAircraft());
                }
            }

            @Override
            public void onFailure(Call<AircraftResponse> call, Throwable t) {
                progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    /**
     * Obtenemos todos la Tripulacion del servidor
     */
    private void getCrews() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.crew();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<CrewResponse> call = endPointsApi.getCrews();
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<CrewResponse>() {
            @Override
            public void onResponse(Call<CrewResponse> call, Response<CrewResponse> response) {
                progressDialog.dismiss();

                if (response.code() == 200) {
                    CrewResponse resp = response.body();

                    bd.list.eliminarCrews();
                    bd.list.listaCapitanInsert("-----------------");
                    for (Crew c : resp.getCrews()) {
                        bd.list.listaCapitanInsert(c.getFull_name());
                    }
                    updateList(bd.list.listaCapitan());
                }
            }

            @Override
            public void onFailure(Call<CrewResponse> call, Throwable t) {
                progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    @Override
    public void displayList() {
        this.iComboSetting.setAdapter(this.iComboSetting.initAdapter(this.strings));
        this.iComboSetting.generateLayout();
    }
}
