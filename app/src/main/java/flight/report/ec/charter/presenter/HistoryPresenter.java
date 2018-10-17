package flight.report.ec.charter.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IHistory;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.adapter.RestApiAdapter;
import flight.report.ec.charter.restApi.modelo.ReportResponse;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements IHistoryPresenter {
    private IHistory iHistory;
    private Context context;
    private BdContructor bd;
    private Preferences preferences;
    private ArrayList<Report> reports = new ArrayList<>();

    private String method;
    private ProgressDialog progressDialog;

    public HistoryPresenter(IHistory iHistory, Context context, String method, ArrayList<Report> local) {
        this.iHistory = iHistory;
        this.context = context;
        this.method = method;
        this.preferences = new Preferences(context);
        this.bd = new BdContructor(context);

        if (local.isEmpty()) {
            this.getHistories();
        } else {
            this.reports = local;
            displayHistories();
        }
    }

    @Override
    public void getHistories() {
        if(method==context.getResources().getString(R.string.presenter_local)) {
            searchBd();
            listIsEmpty();
        } else {
            getHistoryFromServer();
        }
    }

    private void searchBd() {
        this.reports = bd.report.reportTodos();
        Collections.reverse(this.reports);
        this.displayHistories();
    }

    private void listIsEmpty() {
        if (this.reports.isEmpty())
            Mensaje.mensajeCorto(context, context.getResources().getString(R.string.not_histories));
    }

    /**
     * Obtenemos todos los reportes que el usuario ha mandado al server
     */
    public void getHistoryFromServer() {
        progressDialog = Mensaje.progressConsultar(context);
        progressDialog.show();

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gson = restApiAdapter.history();
        EndPointsApi endPointsApi = restApiAdapter.establecerConexionApi(gson);

        Call<ReportResponse> call = endPointsApi.getHistory(preferences.getUsername());
        Log.d("RESPONSE", call.request().url().toString() + " ");
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    ReportResponse resp = response.body();
                    openModal(resp);
                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                progressDialog.dismiss();
                Mensaje.mensajeCorto(context, Mensaje.ERROR_CONEXION);
                Log.e("ERROR", t.toString() + " " + call.toString());
            }
        });
    }

    private void openModal(final ReportResponse resp) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getResources().getString(R.string.syc_report_title));
        if (!resp.getReport().isEmpty()) {
            builder.setMessage(StringUtil.format(context.getResources().getString(R.string.report_syc), resp.getReport().size()));

            builder.setPositiveButton(context.getResources().getString(R.string.syn_up), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Report> reports = bd.report.reportTodos();
                    if (!reports.isEmpty()) {
                        Report lastOne = reports.get(reports.size() - 1);
                        for (Report r : reports) {
                            if (r.equals(lastOne)) {
                                if (lastOne.isSend())
                                    bd.report.reportDelete(r);
                            } else {
                                bd.report.reportDelete(r);
                            }
                        }
                    }

                    for (flight.report.ec.charter.modelo.response.Report r : resp.getReport()) {
                        bd.report.reportInsert(r.getReport());
                        Report aux = bd.report.getLastOne();
                        for (Expenses e : r.getExpenses()) {
                            e.setReport(aux);
                            bd.expense.expensesInsert(e);
                        }
                        for (AircraftReport m : r.getMaintenances()) {
                            m.setReport(aux);
                            bd.maintenance.aircraftInsert(m);
                        }
                        for (Plan p : r.getPlans()) {
                            p.setReport(aux);
                            bd.plan.planInsert(p);
                        }
                    }

                    iHistory.updateList();
                    dialog.dismiss();
                }
            });
        }
        builder.setNegativeButton(context.getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void displayHistories() {
        this.iHistory.setAdapter(this.iHistory.initAdapter(this.reports));
        this.iHistory.generateLayout();
    }

}
