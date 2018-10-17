package flight.report.ec.charter;

import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.Collections;

import flight.report.ec.charter.adaptador.RecyclerHistoryAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.interfaz.IHistory;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.presenter.HistoryPresenter;
import flight.report.ec.charter.presenter.IHistoryPresenter;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Connection;
import flight.report.ec.charter.utils.DialogPhoto;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;

public class HistoryActivity extends BaseActivity implements RecyclerHistoryAdapter.CallbackInterface,
        DialogPhoto.CallbackInterfaceDialog, IHistory {
    private Toolbar toolbar;
    private RecyclerView reciclerHistory;

    private TextView result;
    private EditText search;
    private MenuItem sincronizar;
    private MenuItem searchBar;
    private MenuItem closeSearch;
    private Keyboard keyboard;

    private RecyclerHistoryAdapter adapter;
    private BdContructor bd;
    private Connection connection;

    private IHistoryPresenter iHistoryPresenter;

    private Bundle INSTANCESTATE = null;
    private ArrayList<Report> reportsInstance = new ArrayList<>();
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";
    private String SAVED_INSTANCE_VISIBILITY_SEARCH = "saved_instance_visibility_search";
    private String SAVED_INSTANCE_VISIBILITY_RESULT = "saved_instance_visibility_result";
    private String SAVED_INSTANCE_RESULT_TEXT = "saved_instance_result_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.INSTANCESTATE = savedInstanceState;
        this.keyboard = new Keyboard(HistoryActivity.this);
        this.bd = new BdContructor(HistoryActivity.this);
        this.connection = new Connection(HistoryActivity.this);

        enlazarVista();
        inputUpperCase();
        setToolbar(toolbar, getResources().getString(R.string.history), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CanSavedInstance(this.INSTANCESTATE, "recicler");
        iHistoryPresenter = new HistoryPresenter(this, HistoryActivity.this, getResources().getString(R.string.presenter_local), reportsInstance);
        if (!reportsInstance.isEmpty())
            searchFilter( search.getText().toString() );
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        toolbar         = findViewById(R.id.toolbar);
        reciclerHistory = findViewById(R.id.reciclerHistory);
        onScrollListener(reciclerHistory);

        this.result = findViewById(R.id.result);
        this.search = findViewById(R.id.search);
        this.search.setOnEditorActionListener(this);
        this.search.addTextChangedListener(this);
    }

    /**
     * Obtenemos valores preguardados en la pantalla al cambiar de estado
     **/
    private void CanSavedInstance(Bundle instance, String value) {
        if (instance!=null) {
            if (StringUtil.equals(value, "recicler")) {
                reportsInstance = instance.getParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST);
            } else if (StringUtil.equals(value, "search")) {
                int v_search = instance.getInt(SAVED_INSTANCE_VISIBILITY_SEARCH);
                int v_result = instance.getInt(SAVED_INSTANCE_VISIBILITY_RESULT);
                String s_result = instance.getString(SAVED_INSTANCE_RESULT_TEXT);

                this.search.setVisibility(v_search);
                this.result.setVisibility(v_result);
                this.result.setText(s_result);
                this.searchDisplay(this.search.getVisibility()==View.VISIBLE);
            }
        } else {
            reportsInstance = new ArrayList<>();
        }
    }

    @Override
    public void generateLayout() {
        LinearLayoutManager llm = new LinearLayoutManager(HistoryActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reciclerHistory.setLayoutManager(llm);
    }

    @Override
    public RecyclerHistoryAdapter initAdapter(ArrayList<Report> list) {
        adapter = new RecyclerHistoryAdapter(list, HistoryActivity.this);
        return adapter;
    }

    @Override
    public void setAdapter(RecyclerHistoryAdapter adapter) {
        reciclerHistory.setAdapter(adapter);
    }

    /**
     * Actualidamos la lista
     **/
    @Override
    public void updateList() {
        ArrayList<Report> reports = bd.report.reportTodos();
        Collections.reverse(reports);
        adapter.updateList(reports);
    }

    /**
     * Obtenemos el callback para borrar un item
     */
    @Override
    public void onHandleSelection(int position, String metohd) {
        DialogPhoto.alerDeletetInit(HistoryActivity.this, "Are Your Sure?", position, "report");
    }

    /**
     * Si se elimino el itemm, entonces recarga el reciclerView
     */
    @Override
    public void onHandleSelectionButton(boolean b) {
        if (b) {
            updateList();
        }
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
                    reportsInstance = new ArrayList<>();
                    iHistoryPresenter = new HistoryPresenter(this, HistoryActivity.this, getResources().getString(R.string.presenter_server), reportsInstance);
                } else {
                    Mensaje.mensajeCorto(HistoryActivity.this, Mensaje.INTERNET_CONEXION);
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
        if (adapter!=null) {
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

        currentState.putParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST, (ArrayList<? extends Parcelable>) adapter.getHistory());
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

}
