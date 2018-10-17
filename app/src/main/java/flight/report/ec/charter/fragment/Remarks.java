package flight.report.ec.charter.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.*;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.task.Mail;
import flight.report.ec.charter.task.Server;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.Email;
import flight.report.ec.charter.utils.Mensaje;

/**
 * Created by Jose on 19/1/2018.
 */

public class Remarks extends Fragment {
    private EditText remarks;

    private Send SEND;
    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    public static final String REPORTE_ID = "report_id";
    private boolean haveAircraftReport = false;
    private Preferences PREFERENCES;

    private Mail mail;
    private Server server;
    private Connection connection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_remarks, container, false);

        this.bd = new BdContructor(Remarks.this.getContext());
        this.PREFERENCES = new Preferences(Remarks.this.getContext());
        this.connection = new Connection(Remarks.this.getContext());
        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(REPORTE_ID);
            reporteGlobal = bd.report.reportById(id);
        }
        this.mail = new Mail(Remarks.this.getContext(), Remarks.this, reporteGlobal, haveAircraftReport);
        this.server = new Server(Remarks.this.getContext(), Remarks.this, reporteGlobal, PREFERENCES);
        SEND = bd.send.sendById(1);

        enlazarVista(v);
        loadReport();
        changedRemarks();
        inputUpperCase();

        return v;
    }

    private void inputUpperCase() {
        remarks.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void changedRemarks() {
        remarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reporteGlobal.setRemarks(charSequence.toString());
                bd.report.reportUpdate(reporteGlobal, "remarks");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void loadReport() {
        remarks.setText(reporteGlobal.getRemarks());
    }

    private void enlazarVista(View v) {
        remarks     = v.findViewById(R.id.remarks);
    }

    public void setReporteGlobal(flight.report.ec.charter.modelo.Report reporteGlobal) {
        this.reporteGlobal = reporteGlobal;
    }

    public void sendReport() {
        flight.report.ec.charter.modelo.Report report = bd.report.reportById(reporteGlobal.getId());

        if ( report.getAircraft() == null
                || report.getAircraft().equals("-----------------")
                || report.getCapitan() == null
                || report.getCapitan().equals("-----------------")
                || report.getRoute() == null
                || report.getRoute().isEmpty() ) {
            Mensaje.mensajeLargo(Remarks.this.getContext(),
                    Remarks.this.getActivity().getResources().getString(R.string.error_send_required));
            return;
        }

                /*
                if (report.getHour_final() < report.getHour_initial()) {
                    Mensaje.mensajeLargo(Remarks.this.getContext(),
                            Remarks.this.getActivity().getResources().getString(R.string.error_send_hours));
                    return;
                }*/

        if (this.connection.isOnline()) {
            if (SEND.isMail()) {
                this.mail.send();
                if (SEND.isServer())
                    this.server.send();
            }
            if (SEND.isServer() && !SEND.isMail()) {
                this.server.send();
            }

            if (!SEND.isServer() && !SEND.isMail()) {
                Mensaje.mensajeLargo(Remarks.this.getContext(),
                        Remarks.this.getActivity().getResources().getString(R.string.send_error));
            }
        } else {
            Mensaje.mensajeLargo(Remarks.this.getContext(),
                    Remarks.this.getActivity().getResources().getString(R.string.error_connection));

            reporteGlobal.setPen(true);
            bd.report.reportUpdate(reporteGlobal, "pen");
            Remarks.this.getActivity().finish();
        }
    }

    /**
     * @Depresiable
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (haveAircraftReport) {
                reporteGlobal.setSentMailOpe(true);
                bd.report.reportUpdate(reporteGlobal, "sent_mail_ope");

                sendReport(Email.EMAIL_REPORT, reporteGlobal.getId());
            } else {
                reporteGlobal.setSentMail(true);
                reporteGlobal.setSend(true);
                bd.report.reportUpdate(reporteGlobal, "send");
                bd.report.reportUpdate(reporteGlobal, "sent_mail");

                Remarks.this.getActivity().finish();
            }
        }
    }

    /**
     * @Depresiable
     * */
    public void sendReport(String emailTo, int id) {
        haveAircraftReport = false;

        flight.report.ec.charter.modelo.Report report = bd.report.reportById(id);
        ArrayList<AircraftReport> aircrafts = bd.maintenance.aircraftTodos(report.getId());
        List<String> filePaths = new ArrayList<>();
        int cantImg = 0;

        String subject = Email.titleReportEmail(report);
        String emailText = Email.aircraft(aircrafts, report, filePaths, cantImg);

        Email.send(this, emailTo, subject, emailText, filePaths, false, new ProgressDialog(Remarks.this.getContext()));
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (reporteGlobal != null)
            currentState.putInt(REPORTE_ID, reporteGlobal.getId());
    }


}
