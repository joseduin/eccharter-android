package flight.report.ec.charter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mindorks.paracamera.Camera;

import java.io.File;
import java.io.IOException;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Hora;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.Mensaje;

public class Expensesctivity extends BaseActivity implements DialogPhoto.CallbackInterfaceDialog,
        DialogPhoto.CallbackInterfaceImagen {
    private EditText expenses_total, expenses_description;
    private TextView expenses_photo_path, expenses_currency;
    private ImageButton expenses_btn_photo;
    private Toolbar toolbar;

    private BdContructor bd;
    private Uri photoUri = null;
    private Expenses expenses;
    private Report reporteGlobal;
    private int ID;
    public static final String PHOTO = "photo_path";
    private String mCurrentPhotoPath;
    private Image image;

    private MenuItem done;
    private Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensesctivity);
        this.keyboard = new Keyboard(Expensesctivity.this);
        this.bd = new BdContructor(Expensesctivity.this);
        this.image = new Image(Expensesctivity.this);

        enlazarVista();
        setExpense();

        if (savedInstanceState != null) {
            String photo = savedInstanceState.getString(PHOTO);
            photoUri = photo!= null ? Uri.parse(photo) : null;
            expenses_photo_path.setText( photo != null ? Convert.photoFormat( photoUri.toString() ) : null );
        }

        inputUpperCase();
        setToolbar(toolbar, getResources().getString(R.string.new_expense), true);
    }

    /**
     * Input UpperCase
     */
    private void inputUpperCase() {
        expenses_description.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    /**
     * Cargamos el expense en los campos de la vista
     */
    private void loadExpense() {
        expenses_total.setText( String.valueOf( expenses.getTotal() ) );
        expenses_description.setText( expenses.getDescription() );
        expenses_photo_path.setText(Convert.photoFormat(expenses.getPhoto()) );
        if (expenses.getPhoto() != null)
            photoUri = Uri.parse(expenses.getPhoto());
    }

    /**
     * Obtenemos el ID del expense pasaado por parametro,
     * lo buscamos en la bd
     */
    private void setExpense() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("ID");
            if (ID != 0) {
                expenses = bd.expense.expensesById(ID);
                if (expenses.getPhoto() != null)
                    if (!expenses.getPhoto().equals(""))
                        photoUri = Uri.parse( expenses.getPhoto() );

                loadExpense();
            }
            int id_report = extras.getInt("ID_REPORT");
            reporteGlobal = bd.report.reportById(id_report);
        }
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        expenses_total = findViewById(R.id.expenses_total);
        expenses_description = findViewById(R.id.expenses_description);
        expenses_currency = findViewById(R.id.expenses_currency);
        expenses_photo_path = findViewById(R.id.expenses_photo_path);
        expenses_btn_photo = findViewById(R.id.expenses_btn_photo);
        toolbar = findViewById(R.id.toolbar);

        expenses_photo_path.setOnClickListener(this);
        expenses_btn_photo.setOnClickListener(this);
    }

    /**
     * OnClick EventChat
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expenses_btn_photo:
            case R.id.expenses_photo_path:
                DialogPhoto.init(Expensesctivity.this, photoUri);
                break;
        }
    }

    /**
     * Valdidamos que los campos que se insertaran sean adecuados
     */
    private void validateInsert() {
        String total = expenses_total.getText().toString().trim();
        String description = expenses_description.getText().toString().trim();

        if (!total.equals("") && !description.equals("")) {

            Expenses expenses = new Expenses();
            expenses.setId(ID);
            expenses.setReport( reporteGlobal );
            expenses.setTotal( Double.valueOf( total ) );
            expenses.setCurrency( expenses_currency.getText().toString() );
            expenses.setDescription( description );
            expenses.setPhoto( photoUri != null ? photoUri.toString() : null );

            if (ID == 0)
                bd.expense.expensesInsert(expenses);
            else
                bd.expense.expensesUpdate(expenses);

            goBack();
        } else {
            Mensaje.mensajeCorto(Expensesctivity.this, getResources().getString(R.string.mandatary_total_description));
        }
    }

    /**
     * Mandamos un callback a la pantalla anterior,
     * para que recargue la info
     */
    private void goBack() {
        keyboard.hideKeyboard(getCurrentFocus());
        Intent i = new Intent();
        i.putExtra("RELOAD_EXPENSES", "YES");
        setResult(Activity.RESULT_OK, i);

        finish();
    }

    /**
     * Obtenemos el evento cuando se toma una foto o escoge de la galeria
     */
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
                expenses_photo_path.setText( Convert.photoFormat( photoUri.toString() ));
            }
        } else if (requestCode == DialogPhoto.PICK_PHOTO_CODE) {
            if (data != null) {
                if(resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra("SELECT_IMAGE")!=null) {
                        photoUri = Uri.parse(data.getStringExtra("SELECT_IMAGE"));
                        expenses_photo_path.setText(Convert.photoFormat(photoUri.toString()));
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
     * Guardamos el estado de la data, al girar el tlf
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putString(PHOTO, expenses_photo_path.getText().toString());
    }

    /**
     * Guardamos el estado de la data, al girar el tlf
     */
    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            photoUri = null;
            expenses_photo_path.setText("");
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
