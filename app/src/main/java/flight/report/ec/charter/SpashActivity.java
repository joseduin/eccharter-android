package flight.report.ec.charter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.RuntimePermissionsActivity;
import flight.report.ec.charter.utils.Token;

public class SpashActivity extends RuntimePermissionsActivity implements View.OnClickListener,
        TextView.OnEditorActionListener {

    private EditText pin;
    private Button btn_login;

    private static final int REQUEST_PERMISSIONS = 20;
    private Preferences PREFERENCES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        PREFERENCES = new Preferences(SpashActivity.this);

        enlazarVista();
        enableElementUntilPermissionsGranted(false);
        permissions();
    }

    /**
     * enlazamos vista con controlador
     */
    private void enlazarVista() {
        pin         = findViewById(R.id.pin);
        btn_login   = findViewById(R.id.btn_login);

        pin.setOnEditorActionListener(this);
        btn_login.setOnClickListener(this);
    }

    /**
     * Cuando se tengan los permisos se desbloquean el pin y el boton
     */
    private void enableElementUntilPermissionsGranted(boolean b) {
        pin.setEnabled(b);
        btn_login.setEnabled(b);
    }

    /**
     * Permisos que debe aceptar el usuario
     */
    private void permissions() {
        SpashActivity.super.requestAppPermissions(new
                        String[]{
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                        },
                R.string.app_name, REQUEST_PERMISSIONS);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == REQUEST_PERMISSIONS) {
            enableElementUntilPermissionsGranted(true);
        }
    }

    @Override
    public void onClick(View view) {
        login();
    }

    /**
     * Cuando se le da ok al keyboard
     */
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE
                || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            login();
            return true;
        }
        return false;
    }

    /**
     * Iniciamos sesion
     */
    private void login() {
        if (PREFERENCES.getUsername()==null) {
            openModal();
            return;
        }

        String clave = pin.getText().toString().trim();

        if ( Token.access(clave) ) {
            pin.setText("");
            Mensaje.mensajeCorto(SpashActivity.this, Mensaje.ERROR_LOGIN);
        } else {
            IrA.vistaAndClear(SpashActivity.this, InitActivity.class);
        }
    }

    private void openModal() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(SpashActivity.this);

        builder.setTitle("INSERT YOUR USER NAME");

        final EditText edit = new EditText(SpashActivity.this);
        builder.setView(edit);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String e = edit.getText().toString().trim();

                if (e.equals("")) {
                    Mensaje.mensajeCorto(SpashActivity.this, getResources().getString(R.string.empty));
                    return;
                }

                PREFERENCES.setUsername(e.toUpperCase());
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}