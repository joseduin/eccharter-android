package flight.report.ec.charter.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.Email;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;

public class Mail {
    private ProgressDialog progressDialog;
    private Context context;
    private Fragment fragment;
    private Report reporteGlobal;
    private Boolean haveAircraftReport;

    public Mail(Context context, Fragment fragment, Report reporteGlobal, Boolean haveAircraftReport) {
        this.context = context;
        this.reporteGlobal = reporteGlobal;
        this.fragment = fragment;
        this.haveAircraftReport = haveAircraftReport;
    }

    public void send() {
        new SendEmail(context).execute();
    }

    private class SendEmail extends AsyncTask<Void, Void, String> {

        private BdContructor bd;

        public SendEmail(Context activity) {
            progressDialog = Mensaje.progressEnvio(activity);
            bd = new BdContructor(context);
        }

        @Override
        protected void onPreExecute() {
            if (!haveAircraftReport)
                progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            send(Email.EMAIL, reporteGlobal.getId());
            return "succeess";
        }

        public void send(String emailTo, int id) {

            flight.report.ec.charter.modelo.Report report = bd.report.reportById(id);
            ArrayList<Expenses> expenses = bd.expense.expensestTodos(report.getId());
            ArrayList<AircraftReport> aircrafts = bd.maintenance.aircraftTodos(report.getId());
            ArrayList<Plan> plans = bd.plan.plansTodos(report.getId());
            List<String> filePaths = new ArrayList<>();
            int cantImg = 0;
            String emailText = "";

            String subject = Email.titleNormalEmail(report);
            emailText = emailText.concat(Email.basic(report, filePaths, cantImg));

            if (!expenses.isEmpty()) {
                emailText = emailText.concat(Email.expense(expenses, filePaths, cantImg));
            }

            if (!aircrafts.isEmpty()) {
                emailText = emailText.concat(Email.aircraft(aircrafts, report, filePaths, cantImg));
            }
            emailText = emailText.concat("\n REMARKS:                     " + StringUtil.nullTranform(report.getRemarks()) + "\n\n");
            if (!plans.isEmpty()) {
                emailText = emailText.concat(Email.plan(plans, filePaths, cantImg));
            }

            if (!aircrafts.isEmpty()
                    || report.getEngine1() != 0.0
                    || report.getEngine2() != 0.0) {
                haveAircraftReport = true;
            }

            Email.send(fragment, emailTo, subject, emailText, filePaths, true, progressDialog);
        }

    }

}
