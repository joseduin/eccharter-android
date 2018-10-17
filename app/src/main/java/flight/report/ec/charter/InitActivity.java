package flight.report.ec.charter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import flight.report.ec.charter.aircrafts.AircraftActivity;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Mensaje;

public class InitActivity extends BaseActivity {

    private Button btn_new_report, btn_history, btn_logout, btn_aircraft;
    private Toolbar toolbar;

    private BdContructor bd;
    private ArrayList<Report> reports = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        bd = new BdContructor(InitActivity.this);
        enlazarVista();
        setToolbar(toolbar, getResources().getString(R.string.app_name), false);

        loadData();
    }

    /**
     * Cargamos los valores de la bd
     */
    private void loadData() {
        bd.initDataBase();
    }

    /**
     * OnResume LiveCicle
     */
    @Override
    protected void onResume() {
        super.onResume();
        btnReport();
    }

    /**
     * Configuramos el boton 'report',
     * si existe un reporte como borrador o es uno nuevo
     */
    private void btnReport() {
        reports = bd.report.reportTodos();
        String text;
        if (reports.isEmpty()) {
            text = getResources().getString(R.string.btn_create_report);
        } else {
            if (reports.get( reports.size() - 1 ).isSend()) {
                text = getResources().getString(R.string.btn_create_report);
            } else {
                text = getResources().getString(R.string.btn_continue_report);
            }
        }
        btn_new_report.setText(text);
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        btn_new_report  = findViewById(R.id.btn_new_report);
        btn_history     = findViewById(R.id.btn_history);
        btn_logout      = findViewById(R.id.btn_logout);
        btn_aircraft    = findViewById(R.id.btn_aircraft);
        toolbar         = findViewById(R.id.toolbar);

        btn_new_report.setOnClickListener(this);
        btn_history.setOnClickListener(this);
        btn_aircraft.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    /**
     * OnClick EventChat
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * Si no hay reportes entonces crea uno nuevo
             * Si el ultimo reporte esta enviado, crea uno
             */
            case R.id.btn_new_report:
                int ID = 0;
                Report report = new Report();
                report.setDate(Hora.today());
                report.setSend(false);
                report.setPen(false);
                report.setSentServer(false);
                report.setSentMail(false);
                report.setSentMailOpe(false);

                if (reports.isEmpty()) {
                    bd.report.reportInsert(report);

                    ID = bd.report.ultimoBorrador().getId();
                } else {
                    if (reports.get(reports.size() - 1).isSend()) {
                        bd.report.reportInsert(report);

                        ID = bd.report.ultimoBorrador().getId();
                    }
                }
                IrA.vista(InitActivity.this, ReportActivity.class, ID);
                break;
            case R.id.btn_history:
                IrA.vista(InitActivity.this, HistoryActivity.class);
                break;
            case R.id.btn_aircraft:
                IrA.vista(InitActivity.this, AircraftActivity.class);
                break;
            case R.id.btn_logout:
                IrA.vistaAndClear(InitActivity.this, SpashActivity.class);
                finish();
                break;
        }
    }

    /**
     * Asignamos el menu que tendra la pantalla
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * Configuramos el onClick de los items del menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                IrA.vista(InitActivity.this, SettingActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
