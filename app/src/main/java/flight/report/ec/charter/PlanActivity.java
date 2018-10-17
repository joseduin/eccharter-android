package flight.report.ec.charter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mindorks.paracamera.Camera;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.Mensaje;

public class PlanActivity extends BaseActivity implements DialogPhoto.CallbackInterfaceDialog,
        DialogPhoto.CallbackInterfaceImagen {
    private EditText aircraft_description;
    private TextView aircraft_photo_path;
    private ImageButton aircraft_btn_photo;
    private Toolbar toolbar;

    private BdContructor bd;
    private Uri photoUri = null;
    private Plan aircraftReport;
    private Report reporteGlobal;
    private int ID = 0;
    public static final String PHOTO = "photo_path";
    private String mCurrentPhotoPath;
    private Image image;

    private MenuItem done;
    private Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        this.keyboard = new Keyboard(PlanActivity.this);
        this.bd = new BdContructor(PlanActivity.this);
        this.image = new Image(PlanActivity.this);

        enlazarVista();
        setAircraft();

        if (savedInstanceState != null) {
            String photo = savedInstanceState.getString(PHOTO);
            photoUri = photo != null ? Uri.parse(photo) : null;
            aircraft_photo_path.setText(photo != null ? Convert.photoFormat(photoUri.toString()) : null);
        }

        inputUpperCase();
        setToolbar(toolbar, getResources().getString(R.string.new_plan), true);
    }

    /**
     * Input UpperCase
     */
    private void inputUpperCase() {
        aircraft_description.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    /**
     * Cargamos el reporte de aircraft en los campos de la vista
     */
    private void loadAircraf() {
        aircraft_description.setText(aircraftReport.getDescription());
        aircraft_photo_path.setText(Convert.photoFormat(aircraftReport.getPhoto()));
        if (aircraftReport.getPhoto() != null)
            photoUri = Uri.parse(aircraftReport.getPhoto());
    }

    /**
     * Obtenemos el ID del aircraft report que pasamos por parametros
     * lo buscamos por la bd
     */
    private void setAircraft() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            if (ID != 0) {
                aircraftReport = bd.plan.planById(ID);
                if (aircraftReport.getPhoto() != null)
                    if (!aircraftReport.getPhoto().equals(""))
                        photoUri = Uri.parse(aircraftReport.getPhoto());

                loadAircraf();
            }
            int id_report = extras.getInt("ID_REPORT");
            reporteGlobal = bd.report.reportById(id_report);
        }
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        aircraft_description = findViewById(R.id.aircraft_description);
        aircraft_photo_path = findViewById(R.id.aircraft_photo_path);
        aircraft_btn_photo = findViewById(R.id.aircraft_btn_photo);
        toolbar = findViewById(R.id.toolbar);

        aircraft_photo_path.setOnClickListener(this);
        aircraft_btn_photo.setOnClickListener(this);
    }

    /**
     * OnClick EventChat
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aircraft_btn_photo:
            case R.id.aircraft_photo_path:
                DialogPhoto.init(PlanActivity.this, photoUri);
                break;
        }
    }

    /**
     * Validamos los datos que insertaremos en la bd
     */
    private void validateInsert() {
        String description = aircraft_description.getText().toString().trim();

        if (!description.equals("")) {

            Plan plan = new Plan();
            plan.setId(ID);
            plan.setReport(reporteGlobal);
            plan.setDescription(description);

            plan.setPhoto(photoUri != null ? photoUri.toString() : null);

            if (ID == 0)
                bd.plan.planInsert(plan);
            else
                bd.plan.planUpdate(plan);

            goBack();
        } else {
            Mensaje.mensajeCorto(PlanActivity.this, getResources().getString(R.string.mandatary_route));
        }
    }

    /**
     * Callback a la pantalla anterior, para que act la info
     */
    private void goBack() {
        keyboard.hideKeyboard(getCurrentFocus());
        Intent i = new Intent();
        i.putExtra("RELOAD_PLAN", "YES");
        setResult(Activity.RESULT_OK, i);

        finish();
    }

    /**
     * Callback cuando se toma o elige una foto
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // Android Version > Nuggats
                    handleBigCameraPhoto();
                } else {
                    // Android Version < Nuggats
                    photoUri = data.getData();
                }
                aircraft_photo_path.setText(Convert.photoFormat(photoUri.toString()));
            }
        } else if (requestCode == DialogPhoto.PICK_PHOTO_CODE) {
            if (data != null) {
                if(resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("SELECT_IMAGE") != null) {
                        photoUri = Uri.parse(data.getStringExtra("SELECT_IMAGE"));
                        aircraft_photo_path.setText(Convert.photoFormat(photoUri.toString()));
                    }
                }
            }
        }
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            Uri imageUri = image.getUriFromPath(mCurrentPhotoPath);

            image.savePic(imageUri, getContentResolver());
            galleryAddPic(imageUri);
            mCurrentPhotoPath = null;
        }
    }

    private void galleryAddPic(Uri imageUri) {
        photoUri = imageUri;
    }

    /**
     * Guaramos el estado del tlf, cuando es girado
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putString(PHOTO, photoUri != null ? photoUri.toString() : null);
    }

    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            photoUri = null;
            aircraft_photo_path.setText("");
        }
    }

    @Override
    public void onHandleSelectionButton(String photopath) {
        mCurrentPhotoPath = photopath;
    }

    /**
     * Creamos un menu en el toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        done = menu.findItem(R.id.done);
        if (ID != 0) {
            done.setTitle(getResources().getString(R.string.update));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                validateInsert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Ocultar el teclado a nivel de rootPage
     **/
    public void hideKeyboard(View view) {
        keyboard.hideKeyboard(getCurrentFocus());
    }
}
