package flight.report.ec.charter.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.PhotoRequest;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.modelo.request.Aircraft;
import flight.report.ec.charter.modelo.request.ExpensesReport;
import flight.report.ec.charter.modelo.request.PlanRequest;
import flight.report.ec.charter.modelo.request.ReportRequest;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.MessageResponse;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.Mensaje;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * PENDIENTE: Subir imgs primero a Amazon y luego enviar al servidor las urls
 **/
public class Server {
    private ProgressDialog progressDialog;
    private Context context;
    private Fragment fragment;
    private Report reporteGlobal;
    private Preferences PREFERENCES;

    public Server(Context context, Fragment fragment, Report reporteGlobal, Preferences PREFERENCES) {
        this.context = context;
        this.reporteGlobal = reporteGlobal;
        this.fragment = fragment;
        this.PREFERENCES = PREFERENCES;
    }

    public Server(Context context, Report reporteGlobal, Preferences PREFERENCES) {
        this.context = context;
        this.reporteGlobal = reporteGlobal;
        this.PREFERENCES = PREFERENCES;
    }

    public void send() {
        new SendServer(context).execute();
    }

    private class SendServer extends AsyncTask<Void, Void, String> {
        private BdContructor bd;

        public SendServer(Context activity) {
            bd = new BdContructor(context);

            if (fragment != null)
                progressDialog = Mensaje.progressConsultar(activity, activity.getResources().getString(R.string.resizing_imagen));
        }

        @Override
        protected void onPreExecute() {
            if (fragment != null)
                progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            pushToServer();
            return "succeess";
        }

        private void pushToServer() {
            RestApiAdapter restApiAdapter = new RestApiAdapter();
            Gson gson = restApiAdapter.message();
            EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);
            String username = PREFERENCES.getUsername();

            final Report report = bd.report.reportById(reporteGlobal.getId());
            ArrayList<ExpensesReport> expenses = new ArrayList<>();
            ArrayList<Aircraft> aircrafts = new ArrayList<>();
            ArrayList<PlanRequest> plans = new ArrayList<>();

            for (Expenses e : bd.expense.expensestTodos(report.getId())) {
                expenses.add(new ExpensesReport(e));
            }

            for (AircraftReport a : bd.maintenance.aircraftTodos(report.getId())) {
                aircrafts.add(new Aircraft(a));
            }

            for (Plan p : bd.plan.plansTodos(report.getId())) {
                plans.add(new PlanRequest(p));
            }

            ReportRequest request = new ReportRequest(username, report, expenses, aircrafts, plans);
            Call<MessageResponse> call = endPointsApi.reportRequest(request);
            Log.d("RESPONSE", call.request().url().toString() + " ");
            Log.d("RESPONSE", request.toString() + " ");

            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (fragment!=null)
                        progressDialog.dismiss();

                    Log.d("RESPONSE", response.code() + "");
                    if (response.code() == 200) {
                        MessageResponse m = response.body();
                        Log.d("REPORT", m.getMessage().getMgs());
                        pushImgs(report);

                        Mensaje.mensajeCorto(context, context.getResources().getString(R.string.successserver));
                    } else if (response.code() == 500) {
                        Mensaje.mensajeCorto(context, context.getResources().getString(R.string.code500));
                        return;
                    } else if (response.code() == 503) {
                        Mensaje.mensajeCorto(context, context.getResources().getString(R.string.code503));
                        return;
                    }
                    reporteGlobal.setSend(true);
                    reporteGlobal.setSentServer(true);
                    bd.report.reportUpdate(reporteGlobal, "send");
                    bd.report.reportUpdate(reporteGlobal, "sent_server");

                    if (fragment!=null)
                        fragment.getActivity().finish();
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }

        private void pushImgs(Report report) {
            String username = PREFERENCES.getUsername();
            if (report.getPassengers_photo() != null) {
                pushImgsToServer(report.getId(), "report", Convert.pathImageToByte(report.getPassengers_photo(), context), username);
            }

            for (Expenses e : bd.expense.expensestTodos(report.getId())) {
                if (e.getPhoto() != null) {
                    pushImgsToServer(e.getId(), "expense", Convert.pathImageToByte(e.getPhoto(), context), username);
                }
            }

            for (AircraftReport a : bd.maintenance.aircraftTodos(report.getId())) {
                if (a.getPhoto() != null) {
                    pushImgsToServer(a.getId(), "aircraft", Convert.pathImageToByte(a.getPhoto(), context), username);
                }
            }

            for (Plan p : bd.plan.plansTodos(report.getId())) {
                if (p.getPhoto() != null) {
                    pushImgsToServer(p.getId(), "plan", Convert.pathImageToByte(p.getPhoto(), context), username);
                }
            }
        }

        private void pushImgsToServer(int id, String tipo, String path, String username) {
            RestApiAdapter restApiAdapter = new RestApiAdapter();
            Gson gson = restApiAdapter.message();
            EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

            PhotoRequest request = new PhotoRequest(username + "-" + id, tipo, path);
            Call<MessageResponse> call = endPointsApi.reportPhotoRequest(request);
            Log.d("RESPONSE", call.request().url().toString() + " ");
            Log.d("RESPONSE", request.toString() + " ");

            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    Log.d("RESPONSE", response.code() + "");
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) { }
            });
        }
    }

}
