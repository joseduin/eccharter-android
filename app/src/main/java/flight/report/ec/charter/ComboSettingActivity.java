package flight.report.ec.charter;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.RecyclerComboSettingsAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.interfaz.IComboSetting;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.presenter.ComboSettingPresenter;
import flight.report.ec.charter.presenter.IComboSettingPresenter;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;

public class ComboSettingActivity extends BaseActivity implements RecyclerComboSettingsAdapter.CallbackInterface,
        DialogPhoto.CallbackInterfaceDialog, IComboSetting {
    private RecyclerView reciclerCombo;
    private Toolbar toolbar;
    private FloatingActionButton add_item;

    private RecyclerComboSettingsAdapter adapter;
    private BdContructor bd;
    private String comboName;
    private int LIST;

    private IComboSettingPresenter iComboSettingPresenter;

    private TextView result;
    private EditText search;
    private MenuItem sincronizar;
    private MenuItem searchBar;
    private MenuItem closeSearch;
    private Keyboard keyboard;
    private Connection connection;

    private Bundle INSTANCESTATE = null;
    private ArrayList<String> stringInstance = new ArrayList<>();
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";
    private String SAVED_INSTANCE_VISIBILITY_SEARCH = "saved_instance_visibility_search";
    private String SAVED_INSTANCE_VISIBILITY_RESULT = "saved_instance_visibility_result";
    private String SAVED_INSTANCE_RESULT_TEXT = "saved_instance_result_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_setting);
        this.INSTANCESTATE = savedInstanceState;
        this.keyboard = new Keyboard(ComboSettingActivity.this);
        this.bd = new BdContructor(ComboSettingActivity.this);
        this.connection = new Connection(ComboSettingActivity.this);

        enlazarVista();
        getBundle();
        inputUpperCase();
    }

    @Override
    public void generateLayout() {
        LinearLayoutManager llm = new LinearLayoutManager(ComboSettingActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerCombo.setLayoutManager(llm);
    }

    @Override
    public RecyclerComboSettingsAdapter initAdapter(ArrayList<String> list) {
        adapter = new RecyclerComboSettingsAdapter(list, ComboSettingActivity.this);
        return adapter;
    }

    @Override
    public void setAdapter(RecyclerComboSettingsAdapter adapter) {
        reciclerCombo.setAdapter(adapter);
    }

    @Override
    public void updateList(ArrayList<String> list) {
        adapter.updateList(list);
    }

    /**
     * Obtenemos el tipo de listado que vamos a mostrar en pantalla
     *  1- Customer
     *  2- Aircraft
     *  3- Captain / Copilot
     */
    private void getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            LIST = extras.getInt("ID");
            comboName =  (LIST == 1) ? getResources().getString(R.string.customer) :
                         (LIST == 2) ? getResources().getString(R.string.aircraft) :
                                       getResources().getString(R.string.capitan_copilot);
            setToolbar(toolbar, comboName, true);
            CanSavedInstance(INSTANCESTATE, "recicler");
            iComboSettingPresenter = new ComboSettingPresenter(this, ComboSettingActivity.this, getResources().getString(R.string.presenter_local), stringInstance, LIST, comboName);
        }
    }

    private void CanSavedInstance(Bundle instance, String value) {
        if (instance!=null) {
            if (StringUtil.equals(value, "recicler")) {
                stringInstance = instance.getStringArrayList(SAVED_INSTANCE_RECICLER_LIST);
            } else if (StringUtil.equals(value, "search")) {
                int v_search = instance.getInt(SAVED_INSTANCE_VISIBILITY_SEARCH);
                int v_result = instance.getInt(SAVED_INSTANCE_VISIBILITY_RESULT);
                String s_result = instance.getString(SAVED_INSTANCE_RESULT_TEXT);

                this.search.setVisibility(v_search);
                this.result.setVisibility(v_result);
                this.result.setText(s_result);
                this.searchDisplay(this.search.getVisibility()== View.VISIBLE);
            }
        } else {
            stringInstance = new ArrayList<>();
        }
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        add_item      = findViewById(R.id.add_item);
        toolbar       = findViewById(R.id.toolbar);
        reciclerCombo = findViewById(R.id.reciclerCombo);
        add_item.setOnClickListener(this);
        onScrollListener(reciclerCombo);

        this.result = findViewById(R.id.result);
        this.search = findViewById(R.id.search);
        this.search.setOnEditorActionListener(this);
        this.search.addTextChangedListener(this);
    }

    /**
     * Callback del adapter
     * obtenemos el tipo de metodo y la posicion del item
     */
    @Override
    public void onHandleSelection(int pos, String metohd) {
        String table = LIST == 1 ? "listCustomer" : LIST == 2 ? "listAircraft" : "listCapitanCopilot";

        String item = getList().get(pos);
        int position =
                LIST == 1 ? bd.list.obtenerPosInListado( item, ConstantesBaseDatos.TABLE_LIST_CUSTOMER) :
                        LIST == 2 ?
                                bd.list.obtenerPosInListado( item, ConstantesBaseDatos.TABLE_LIST_AIRCRAFT) :
                                bd.list.obtenerPosInListado( item, ConstantesBaseDatos.TABLE_LIST_CAPITAN);

        switch (metohd) {
            case "edit":
                modalCreateOrEditComboItem(item, table, metohd, position);
                break;
            case "delete":
                DialogPhoto.alerDeletetInit(ComboSettingActivity.this, "ARE YOU SURE?", position, table);
                break;
        }
    }

    /**
     * Si el item ha sido modificado o eliminado, entonces debemos buscarlo
     * en los reportes donde estaban antes, para ponerle un null a dicho atributo
     */
    private void removeElementDeleteOrUpdateFromExistReport(String item, String atributo) {
        for (Report r : bd.report.reportTodos()) {
            switch (atributo) {
                case "listCustomer":
                    if (r.getCustomer() != null)
                        if (r.getCustomer().equals(item)) {
                            r.setCustomer(null);
                            bd.report.reportUpdate(r, "customer");
                        }
                    break;
                case "listCapitanCopilot":
                    if (r.getCopilot() != null)
                        if (r.getCopilot().equals(item)) {
                            r.setCopilot(null);
                            bd.report.reportUpdate(r, "copilot");
                        }
                    if (r.getCapitan() != null)
                        if (r.getCapitan().equals(item)) {
                            r.setCapitan(null);
                            bd.report.reportUpdate(r, "capitan");
                        }
                    break;
                case "listAircraft":
                    if (r.getAircraft() != null)
                        if (r.getAircraft().equals(item) && atributo.equals("listAircraft")) {
                        r.setAircraft(null);
                        bd.report.reportUpdate(r, "aircraft");
                    }
                    break;
            }
        }
    }

    /**
     * Creamos una ventana emergente para crear un nuevo item
     * o modificar uno existente
     */
    private void modalCreateOrEditComboItem(final String value, final String table, final String metohd, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComboSettingActivity.this);

        String title = position == 0 ? "New " + comboName : "Edit " + comboName;
        builder.setTitle(title);

        final EditText edit = new EditText(ComboSettingActivity.this);
        edit.setText(value);
        edit.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        builder.setView(edit);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String e = edit.getText().toString().trim();

                if (e.equals("")) {
                    Mensaje.mensajeCorto(ComboSettingActivity.this, getResources().getString(R.string.empty));
                    return;
                }
                if (metohd.equals("new")) {
                    createNewItem(e, table);
                } else if (metohd.equals("edit")) {
                    editItem(e, table, position);
                    removeElementDeleteOrUpdateFromExistReport(value, table);
                }
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Guardamos el item modificado en la bd
     */
    private void editItem(String e, String table, int id) {
        switch (table) {
            case "listCustomer":
                bd.list.actualizarListado(e, id, ConstantesBaseDatos.TABLE_LIST_CUSTOMER);
                break;
            case "listAircraft":
                bd.list.actualizarListado(e, id, ConstantesBaseDatos.TABLE_LIST_AIRCRAFT);
                break;
            case "listCapitanCopilot":
                bd.list.actualizarListado(e, id, ConstantesBaseDatos.TABLE_LIST_CAPITAN);
                break;
        }
        adapter.updateList(getList());
        searchFilter( search.getText().toString() );
    }

    /**
     * Guardamos el item nuevo en la bd
     */
    private void createNewItem(String e, String table) {
        switch (table) {
            case "listCustomer":
                bd.list.listaCustomerInsert(e);
                break;
            case "listAircraft":
                bd.list.listaAircraftInsert(e);
                break;
            case "listCapitanCopilot":
                bd.list.listaCapitanInsert(e);
                break;
        }
        adapter.updateList(getList());
        searchFilter( search.getText().toString() );
    }

    /**
     * Si eliminamos un item, entonces el callback nos avisara
     * para recargar la data del recicler
     */
    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            adapter.updateList(getList());
            searchFilter( search.getText().toString() );
        }
    }

    private ArrayList<String> getList() {
        ArrayList<String> aux = LIST == 1 ? bd.list.listaCustomer() : LIST == 2 ? bd.list.listaAircraft() : bd.list.listaCapitan();
        ArrayList<String> strings = new ArrayList<>();
        for (String r : aux) {
            if (!r.equals("-----------------"))
                strings.add(r);
        }
        return strings;
    }

    /**
     * Creamos un menu en el toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_combo_setting, menu);
        return true;
    }

    /**
     * Obtenemos los iconosToolbar
     * Verificamos si hay cambios guardados en el estado del searchbar
     **/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        sincronizar = menu.findItem(R.id.sincronizar);
        searchBar = menu.findItem(R.id.search);
        closeSearch = menu.findItem(R.id.search_close);
        CanSavedInstance(this.INSTANCESTATE, "search");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sincronizar:
                if (connection.isOnline()) {
                stringInstance = new ArrayList<>();
                iComboSettingPresenter = new ComboSettingPresenter(this, ComboSettingActivity.this, getResources().getString(R.string.presenter_server), stringInstance, LIST, comboName);
                } else {
                    Mensaje.mensajeCorto(ComboSettingActivity.this, Mensaje.INTERNET_CONEXION);
                }
                return true;
            case R.id.search:
                searchDisplay(true);
                return true;
            case R.id.search_close:
                clearSearch();
                result.setVisibility(View.GONE);
                keyboard.hideKeyboard(getCurrentFocus());
                searchDisplay(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Cerramos el searchbar
     **/
    private void clearSearch() {
        this.search.setText("");
        this.adapter.filter("");
    }

    /**
     * Mostramos/Ocultamos el Searchbar
     **/
    private void searchDisplay(boolean b) {
        setToolbarBackButton(!b);
        search.setVisibility(b ? View.VISIBLE : View.GONE);
        searchBar.setVisible(!b);
        sincronizar.setVisible(!b);
        closeSearch.setVisible(b);
    }

    /**
     * Instanciamos el EditText en mayuscula
     **/
    private void inputUpperCase() {
        search.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    /**
     * Keyboad boton DONE
     **/
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH
                || keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
            keyboard.hideKeyboard(getCurrentFocus());
            searchFilter( search.getText().toString() );
            return true;
        }
        return false;
    }

    /**
     * Denotamos la accion de escribir en el searchbar
     **/
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchFilter( charSequence.toString() );
    }

    /**
     * Filtramos la lista por el searchbar
     **/
    private void searchFilter(String filter) {
        if (adapter!=null && !search.getText().toString().isEmpty()) {
            int size = this.adapter.filter(filter);
            if (result.getVisibility()==View.GONE)
                result.setVisibility(View.VISIBLE);
            result.setText(StringUtil.format(getResources().getString(R.string.results), size));
        }
    }

    /**
     * Guardamos el estado actual de la pantalla
     **/
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putStringArrayList(SAVED_INSTANCE_RECICLER_LIST, adapter.getList());
        currentState.putInt(SAVED_INSTANCE_VISIBILITY_SEARCH, search.getVisibility());
        currentState.putInt(SAVED_INSTANCE_VISIBILITY_RESULT, result.getVisibility());
        currentState.putString(SAVED_INSTANCE_RESULT_TEXT, StringUtil.nullTranform(result.getText().toString()));
    }

    /**
     * Ocultar el teclado a nivel de rootPage
     **/
    public void hideKeyboard(View view) {
        keyboard.hideKeyboard(getCurrentFocus());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_item:
                String table = LIST == 1 ? "listCustomer" : LIST == 2 ? "listAircraft" : "listCapitanCopilot";
                modalCreateOrEditComboItem("", table, "new", 0);
                break;
        }
    }

}