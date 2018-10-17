package flight.report.ec.charter.aircrafts;

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

import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.RecyclerAircraftTabAdapter;
import flight.report.ec.charter.interfaz.IAircrafts;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.presenter.AircraftsPresenter;
import flight.report.ec.charter.presenter.IAircraftsPresenter;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.StringUtil;

public class AircraftActivity extends BaseActivity implements IAircrafts {
    private RecyclerView recicler;
    private Toolbar toolbar;

    private TextView result;
    private EditText search;
    private MenuItem searchBar;
    private MenuItem closeSearch;
    private Keyboard keyboard;

    private RecyclerAircraftTabAdapter adapter;
    private IAircraftsPresenter iAircraftsPresenter;

    private Bundle INSTANCESTATE = null;
    private ArrayList<Aircraft> aircraftsInstance = new ArrayList<>();
    private String SAVED_INSTANCE_RECICLER_LIST = "saved_instance_recicler_list";
    private String SAVED_INSTANCE_VISIBILITY_SEARCH = "saved_instance_visibility_search";
    private String SAVED_INSTANCE_VISIBILITY_RESULT = "saved_instance_visibility_result";
    private String SAVED_INSTANCE_RESULT_TEXT = "saved_instance_result_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft2);
        this.INSTANCESTATE = savedInstanceState;
        this.keyboard = new Keyboard(AircraftActivity.this);

        enlazarVista();
        toolbarConf();
        inputUpperCase();
        CanSavedInstance(savedInstanceState, "recicler");
        iAircraftsPresenter = new AircraftsPresenter(this, AircraftActivity.this, this.aircraftsInstance);
    }

    private void toolbarConf() {
        setToolbar(toolbar, getResources().getString(R.string.aircrafts), true);
    }

    private void enlazarVista() {
        this.recicler   = findViewById(R.id.recicler);
        this.toolbar    = findViewById(R.id.toolbar);
        onScrollListener(recicler);

        this.result = findViewById(R.id.result);
        this.search = findViewById(R.id.search);
        this.search.setOnEditorActionListener(this);
        this.search.addTextChangedListener(this);
    }

    private void CanSavedInstance(Bundle instance, String value) {
        if (instance!=null) {
            if (StringUtil.equals(value, "recicler")) {
                aircraftsInstance = instance.getParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST);
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
            aircraftsInstance = new ArrayList<>();
        }
    }

    @Override
    public void generateLayout() {
        LinearLayoutManager llm = new LinearLayoutManager(AircraftActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recicler.setLayoutManager(llm);
    }

    @Override
    public RecyclerAircraftTabAdapter initAdapter(ArrayList<Aircraft> list) {
        adapter = new RecyclerAircraftTabAdapter(list, AircraftActivity.this);
        return adapter;
    }

    @Override
    public void setAdapter(RecyclerAircraftTabAdapter adapter) {
        recicler.setAdapter(adapter);
    }

    /**
     * Actualidamos la lista
     **/
    @Override
    public void updateList(ArrayList<Aircraft> list) {
        adapter.updateList(list);
    }

    /**
     * Creamos un menu en el toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    /**
     * Obtenemos los iconosToolbar
     * Verificamos si hay cambios guardados en el estado del searchbar
     **/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchBar = menu.findItem(R.id.search);
        closeSearch = menu.findItem(R.id.search_close);
        CanSavedInstance(this.INSTANCESTATE, "search");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        int size = this.adapter.filter(filter);
        if (result.getVisibility()==View.GONE)
            result.setVisibility(View.VISIBLE);
        result.setText(StringUtil.format(getResources().getString(R.string.results), size));
    }

    /**
     * Guardamos el estado actual de la pantalla
     **/
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(SAVED_INSTANCE_RECICLER_LIST, (ArrayList<? extends Parcelable>) adapter.getAircrafts());
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
