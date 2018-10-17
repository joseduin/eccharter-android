package flight.report.ec.charter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Send;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.IrA;

public class SettingActivity extends BaseActivity {
    private LinearLayout captainContent, aircraftContent, customerContent;
    private Button customer, aircraft, capitan_copilot;
    private Switch switchServer, switchMail;
    private Toolbar toolbar;
    private EditText user_name;

    private Send SEND;
    private BdContructor bd;
    private Preferences PREFERENCES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bd = new BdContructor(SettingActivity.this);
        PREFERENCES = new Preferences(SettingActivity.this);
        SEND = bd.send.sendById(1);
        enlazarVista();

        setToolbar(toolbar, getResources().getString(R.string.settings), true);
        switchLoad();
        switchChange();
        userNameLoadAndChange();
    }

    private void inputUpperCase() {
        user_name.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    private void userNameLoadAndChange() {
        inputUpperCase();

        user_name.setText(PREFERENCES.getUsername());

        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PREFERENCES.setUsername( charSequence.toString() );
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void switchLoad() {
        switchServer.setChecked(SEND.isServer());
        switchMail.setChecked(SEND.isMail());
    }

    private void switchChange() {
        switchServer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SEND.setServer(isChecked);
                bd.send.sendUpdate(SEND, "server");
            }
        });

        switchMail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SEND.setMail(isChecked);
                bd.send.sendUpdate(SEND, "mail");
            }
        });
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        toolbar = findViewById(R.id.toolbar);
        switchServer = findViewById(R.id.switchServer);
        switchMail = findViewById(R.id.switchMail);
        user_name = findViewById(R.id.user_name);
        captainContent = findViewById(R.id.captainContent);
        aircraftContent = findViewById(R.id.aircraftContent);
        customerContent = findViewById(R.id.customerContent);
        customer = findViewById(R.id.customer);
        aircraft = findViewById(R.id.aircraft);
        capitan_copilot = findViewById(R.id.capitan_copilot);

        captainContent.setOnClickListener(this);
        customerContent.setOnClickListener(this);
        aircraftContent.setOnClickListener(this);
        customer.setOnClickListener(this);
        aircraft.setOnClickListener(this);
        capitan_copilot.setOnClickListener(this);
    }

    /**
     * OnClick EventChat
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.customerContent:
            case R.id.customer:
                IrA.vista(SettingActivity.this, ComboSettingActivity.class, 1);
                break;
            case R.id.aircraftContent:
            case R.id.aircraft:
                IrA.vista(SettingActivity.this, ComboSettingActivity.class, 2);
                break;
            case R.id.captainContent:
            case R.id.capitan_copilot:
                IrA.vista(SettingActivity.this, ComboSettingActivity.class, 3);
                break;
        }
    }

}
