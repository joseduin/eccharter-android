package flight.report.ec.charter.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.mindorks.paracamera.Camera;

import flight.report.ec.charter.BuildConfig;
import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.Combo;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.Keyboard;

/**
 * Created by Jose on 19/1/2018.
 */

public class Basic extends Fragment implements View.OnClickListener, DialogPhoto.CallbackInterfaceDialog,
        DialogPhoto.CallbackInterfaceImagen, DialogPhoto.CallbackComboUpdate {

    private Spinner customer, aircraft, capitan, copilot;
    private EditText passengers, dateText;
    private ImageButton add_customer, photo_passengers, add_aircraft, add_capitan, add_copilot;
    private TextView passengers_path;
    private CheckBox cockpi;

    private Keyboard keyboard;
    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    private ArrayAdapter<String> adapterCustomer, adapterAircraft, adapterCapitan, adapterCopilot;
    private ArrayList<String> listAircrafts, listCaptain, listCustomer;
    private Uri photoUri = null;
    private Image image;

    private Calendar myCalendar = Calendar.getInstance();

    public static final String REPORTE_ID = "report_id";
    private String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_basic, container, false);
        this.keyboard = new Keyboard(Basic.this.getContext());
        this.bd = new BdContructor(Basic.this.getContext());
        this.image = new Image(Basic.this.getContext());

        enlazarVista(v);

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(REPORTE_ID);
            reporteGlobal = bd.report.reportById(id);
            if (reporteGlobal.getPassengers_photo() != null) {
                photoUri = Uri.parse(reporteGlobal.getPassengers_photo());
            }
        }
        loadCombos();
        loadReport();
        changedValues();
        inputUpperCase();
        hideKeyboard();

        return v;
    }

    private void inputUpperCase() {
        passengers.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        dateText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void changedValues() {
        cockpi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                reporteGlobal.setCockpit( isChecked );
                bd.report.reportUpdate(reporteGlobal, "cockpit");
            }
        });
        passengers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reporteGlobal.setPassengers(charSequence.toString());
                bd.report.reportUpdate(reporteGlobal, "passangers");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setCustomer( validateSpinnerSelected(customer) );
                bd.report.reportUpdate(reporteGlobal, "customer");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        aircraft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setAircraft( validateSpinnerSelected(aircraft) );
                bd.report.reportUpdate(reporteGlobal, "aircraft");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        capitan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setCapitan(validateSpinnerSelected(capitan));
                bd.report.reportUpdate(reporteGlobal, "capitan");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        copilot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reporteGlobal.setCopilot( validateSpinnerSelected(copilot) );
                bd.report.reportUpdate(reporteGlobal, "copilot");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Basic.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String validateSpinnerSelected(Spinner spinner) {
        return spinner.getSelectedItemPosition()==0 ? null : spinner.getSelectedItem().toString();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String fecha = Hora.sdf.format(myCalendar.getTime());
        dateText.setText(fecha);
        reporteGlobal.setDate( fecha );
        bd.report.reportUpdate(reporteGlobal, "date");
    }

    private void loadCombos() {
        listCustomer = bd.list.listaCustomer();
        listAircrafts = bd.list.listaAircraft();
        listCaptain = bd.list.listaCapitan();

        adapterCustomer = Combo.cargar(customer, listCustomer, Basic.this.getContext());
        adapterAircraft = Combo.cargar(aircraft, listAircrafts, Basic.this.getContext());
        adapterCapitan = Combo.cargar(capitan, listCaptain, Basic.this.getContext());
        adapterCopilot = Combo.cargar(copilot, listCaptain, Basic.this.getContext());
    }

    private void loadReport() {
        int pos;
        if (!listCustomer.isEmpty()) {
            pos = bd.list.listPosition(listCustomer, reporteGlobal.getCustomer()==null ? "" : reporteGlobal.getCustomer());
            if (pos == -1) {
                bd.list.listaCustomerInsert(reporteGlobal.getCustomer());
                listCustomer = bd.list.listaCustomer();
                adapterCustomer.clear();
                adapterCustomer.addAll(listCustomer);
                adapterCustomer.notifyDataSetChanged();
            }
            customer.setSelection(bd.list.listPosition(listCustomer, reporteGlobal.getCustomer()==null ? "" : reporteGlobal.getCustomer()));
        }
        if (!listAircrafts.isEmpty()) {
            pos = bd.list.listPosition(listAircrafts, reporteGlobal.getAircraft()==null ? "" : reporteGlobal.getAircraft());
            if (pos == -1) {
                bd.list.listaAircraftInsert(reporteGlobal.getAircraft());
                listAircrafts = bd.list.listaAircraft();
                adapterAircraft.clear();
                adapterAircraft.addAll(listAircrafts);
                adapterAircraft.notifyDataSetChanged();
            }
            aircraft.setSelection(bd.list.listPosition(listAircrafts, reporteGlobal.getAircraft()==null ? "" : reporteGlobal.getAircraft()));
        }
        if (!listCaptain.isEmpty()) {
            pos = bd.list.listPosition(listCaptain, reporteGlobal.getCapitan()==null ? "" : reporteGlobal.getCapitan());
            if (pos == -1) {
                bd.list.listaCapitanInsert(reporteGlobal.getCapitan());
                listCaptain = bd.list.listaCapitan();
                adapterCapitan.clear();
                adapterCapitan.addAll(listCaptain);
                adapterCapitan.notifyDataSetChanged();

                adapterCopilot.clear();
                adapterCopilot.addAll(listCaptain);
                adapterCopilot.notifyDataSetChanged();
            }
            pos = bd.list.listPosition(listCaptain, reporteGlobal.getCopilot()==null ? "" : reporteGlobal.getCopilot());
            if (pos == -1) {
                bd.list.listaCapitanInsert(reporteGlobal.getCopilot());
                listCaptain = bd.list.listaCapitan();
                adapterCapitan.clear();
                adapterCapitan.addAll(listCaptain);
                adapterCapitan.notifyDataSetChanged();

                adapterCopilot.clear();
                adapterCopilot.addAll(listCaptain);
                adapterCopilot.notifyDataSetChanged();
            }
            capitan.setSelection(bd.list.listPosition(listCaptain, reporteGlobal.getCapitan()==null ? "" : reporteGlobal.getCapitan()));
            copilot.setSelection(bd.list.listPosition(listCaptain, reporteGlobal.getCopilot()==null ? "" : reporteGlobal.getCopilot()));
        }

        cockpi.setChecked( reporteGlobal.isCockpit() );
        dateText.setText( reporteGlobal.getDate() );
        passengers.setText( reporteGlobal.getPassengers() );
        if (reporteGlobal.getPassengers_photo() != null) {
            passengers_path.setText(Convert.photoFormat(reporteGlobal.getPassengers_photo()));
            photoUri = Uri.parse( reporteGlobal.getPassengers_photo() );
        }
    }

    private void enlazarVista(View v) {
        customer        = v.findViewById(R.id.customer);
        aircraft        = v.findViewById(R.id.aircraft);
        capitan         = v.findViewById(R.id.capitan);
        copilot         = v.findViewById(R.id.copilot);
        passengers      = v.findViewById(R.id.passengers);
        dateText        = v.findViewById(R.id.date);
        passengers_path = v.findViewById(R.id.passengers_path);
        add_customer    = v.findViewById(R.id.add_customer);
        photo_passengers= v.findViewById(R.id.photo_passengers);
        add_aircraft    = v.findViewById(R.id.add_aircraft);
        add_capitan     = v.findViewById(R.id.add_capitan);
        add_copilot     = v.findViewById(R.id.add_copilot);
        cockpi          = v.findViewById(R.id.cockpi);

        add_customer.setOnClickListener(this);
        photo_passengers.setOnClickListener(this);
        add_aircraft.setOnClickListener(this);
        add_capitan.setOnClickListener(this);
        add_copilot.setOnClickListener(this);
        passengers_path.setOnClickListener(this);
    }

    private void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void setReporteGlobal(Report reporteGlobal) {
        this.reporteGlobal = reporteGlobal;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_customer:
                DialogPhoto.comboInit(Basic.this, adapterCustomer, "customer");
                break;
            case R.id.passengers_path:
            case R.id.photo_passengers:
                DialogPhoto.init(Basic.this, photoUri);
                break;
            case R.id.add_aircraft:
                DialogPhoto.comboInit(Basic.this, adapterAircraft, "aircraft");
                break;
            case R.id.add_capitan:
                DialogPhoto.comboInit(Basic.this, adapterCapitan, adapterCopilot, "capitan");
                break;
            case R.id.add_copilot:
                DialogPhoto.comboInit(Basic.this, adapterCopilot, adapterCapitan, "copilot");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            if(resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // Android Version > Nuggats
                    handleBigCameraPhoto();
                } else {
                    // Android Version < Nuggats
                    photoUri = data.getData();
                }
                passengers_path.setText( Convert.photoFormat(photoUri.toString()) );

                reporteGlobal.setPassengers_photo( photoUri.toString() );
                bd.report.reportUpdate(reporteGlobal, "passengers_photo");
            }
        } else if (requestCode == DialogPhoto.PICK_PHOTO_CODE) {
            if (data != null) {
                if(resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("SELECT_IMAGE")!=null) {
                        photoUri = Uri.parse(data.getStringExtra("SELECT_IMAGE"));
                        reporteGlobal.setPassengers_photo(photoUri.toString());

                        bd.report.reportUpdate(reporteGlobal, "passengers_photo");
                        passengers_path.setText(Convert.photoFormat(photoUri.toString()));
                    }
                }
            }
        }
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            Uri imageUri = image.getUriFromPath(mCurrentPhotoPath);

            image.savePic(imageUri, Basic.this.getActivity().getContentResolver());
            galleryAddPic(imageUri);
            mCurrentPhotoPath = null;
        }
    }

    private void galleryAddPic(Uri imageUri) {
        photoUri = imageUri;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        if (reporteGlobal != null)
            currentState.putInt(REPORTE_ID, reporteGlobal.getId());
    }

    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            photoUri = null;
            passengers_path.setText("");

            reporteGlobal.setPassengers_photo( null );
            bd.report.reportUpdate(reporteGlobal, "passengers_photo");
        }
    }

    @Override
    public void onHandleSelectionButton(String photopath) {
        mCurrentPhotoPath = photopath;
    }

    @Override
    public void onHandleComboUpdate() {
        keyboard.hideKeyboard(Basic.this.getActivity().getCurrentFocus());
    }
}
