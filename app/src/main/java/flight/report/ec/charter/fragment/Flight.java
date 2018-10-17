package flight.report.ec.charter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.utils.Combo;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.Number;

/**
 * Created by Jose on 19/1/2018.
 */

public class Flight extends Fragment {

    private EditText route, gps_flight_time, hour_initial, hour_final, long_time, engine1, engine2;
    private Spinner spinner_engine2, spinner_engine1;

    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;

    public static final String REPORTE_ID = "report_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flight, container, false);
        bd = new BdContructor(Flight.this.getContext());
        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(REPORTE_ID);
            reporteGlobal = bd.report.reportById(id);
        }

        enlazarVista(v);
        loadCombos();
        editDecimal();
        loadReport();
        changedvalues();
        calculateLong();
        inputUpperCase();

        return v;
    }

    private void loadCombos() {
        Combo.cargar(spinner_engine2, bd.list.listaEngine(), Flight.this.getContext());
        Combo.cargar(spinner_engine1, bd.list.listaEngine(), Flight.this.getContext());
    }

    private void inputUpperCase() {
        route.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void changedvalues() {
        spinner_engine1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setComboEngine1( spinner_engine1.getSelectedItem().toString() );
                bd.report.reportUpdate(reporteGlobal, "combo_engine1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        spinner_engine2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setComboEngine2( spinner_engine2.getSelectedItem().toString() );
                bd.report.reportUpdate(reporteGlobal, "combo_engine2");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        route.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reporteGlobal.setRoute(charSequence.toString());
                bd.report.reportUpdate(reporteGlobal, "route");
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        gps_flight_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    double d = 0.0;
                    try {
                        d = Double.valueOf(charSequence.toString());
                        reporteGlobal.setGps_flight_time(d);
                        bd.report.reportUpdate(reporteGlobal, "gps_flight_time");
                    } catch (Exception e) {
                        Mensaje.mensajeCorto(Flight.this.getContext(), Flight.this.getResources().getString(R.string.error_decimal));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        hour_initial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    try {
                        reporteGlobal.setHour_initial(Double.valueOf(charSequence.toString()));
                        bd.report.reportUpdate(reporteGlobal, "hour_initial");
                        calculateLong();
                    } catch (Exception e) {
                        Mensaje.mensajeCorto(Flight.this.getContext(), Flight.this.getResources().getString(R.string.error_decimal));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        hour_final.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    try {
                        reporteGlobal.setHour_final(Double.valueOf(charSequence.toString()));
                        bd.report.reportUpdate(reporteGlobal, "hour_final");
                        calculateLong();
                    } catch (Exception e) {
                        Mensaje.mensajeCorto(Flight.this.getContext(), Flight.this.getResources().getString(R.string.error_decimal));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        engine1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    try {
                        reporteGlobal.setEngine1(Double.valueOf(charSequence.toString()));
                        bd.report.reportUpdate(reporteGlobal, "engine1");
                        calculateLong();
                    } catch (Exception e) {
                        Mensaje.mensajeCorto(Flight.this.getContext(), Flight.this.getResources().getString(R.string.error_decimal));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        engine2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    try {
                        reporteGlobal.setEngine2(Double.valueOf(charSequence.toString()));
                        bd.report.reportUpdate(reporteGlobal, "engine2");
                        calculateLong();
                    } catch (Exception e) {
                        Mensaje.mensajeCorto(Flight.this.getContext(), Flight.this.getResources().getString(R.string.error_decimal));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void calculateLong() {
        if (!hour_initial.getText().toString().equals("") &&
                !hour_final.getText().toString().equals("") ) {

            double time = Double.valueOf(hour_final.getText().toString())
                    - Double.valueOf(hour_initial.getText().toString());

            if (time >= 0.0)
                long_time.setText( Number.decimalFormat(time, 1) );
                reporteGlobal.setLong_time(time);
                bd.report.reportUpdate(reporteGlobal, "log_time");
        }
    }

    private void loadReport() {
        spinner_engine1.setSelection( bd.list.listPosition(bd.list.listaEngine(), reporteGlobal.getComboEngine1() == null ? "" : reporteGlobal.getComboEngine1() ) );
        spinner_engine2.setSelection( bd.list.listPosition(bd.list.listaEngine(), reporteGlobal.getComboEngine2() == null ? "" : reporteGlobal.getComboEngine2() ) );

        route.setText( reporteGlobal.getRoute() );
        hour_initial.setText( String.valueOf( reporteGlobal.getHour_initial() ) );
        hour_final.setText( String.valueOf( reporteGlobal.getHour_final() ) );
        long_time.setText( String.valueOf( reporteGlobal.getLong_time() ) );
        gps_flight_time.setText( String.valueOf( reporteGlobal.getGps_flight_time() ) );
        engine1.setText( String.valueOf( reporteGlobal.getEngine1() ) );
        engine2.setText( String.valueOf( reporteGlobal.getEngine2() ) );
    }

    private void editDecimal() {
        gps_flight_time.setFilters(new InputFilter[] {Number.decimalEditText(1)} );
        hour_initial.setFilters(new InputFilter[] {Number.decimalEditText(1)} );
        hour_final.setFilters(new InputFilter[] {Number.decimalEditText(1)} );
        engine1.setFilters(new InputFilter[] {Number.decimalEditText(2)} );
        engine2.setFilters(new InputFilter[] {Number.decimalEditText(2)} );
    }

    private void enlazarVista(View v) {
        route           = v.findViewById(R.id.route);
        gps_flight_time = v.findViewById(R.id.gps_flight_time);
        hour_initial    = v.findViewById(R.id.hour_initial);
        hour_final      = v.findViewById(R.id.hour_final);
        long_time       = v.findViewById(R.id.long_time);
        engine1         = v.findViewById(R.id.engine1);
        engine2         = v.findViewById(R.id.engine2);
        spinner_engine2 = v.findViewById(R.id.spinner_engine2);
        spinner_engine1 = v.findViewById(R.id.spinner_engine1);
    }

    public void setReporteGlobal(flight.report.ec.charter.modelo.Report reporteGlobal) {
        this.reporteGlobal = reporteGlobal;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (reporteGlobal != null)
            currentState.putInt(REPORTE_ID, reporteGlobal.getId());
    }

}
