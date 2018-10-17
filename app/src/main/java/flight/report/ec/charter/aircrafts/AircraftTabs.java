package flight.report.ec.charter.aircrafts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.PagerAdapter;
import flight.report.ec.charter.aircrafts.fragment.Chat;
import flight.report.ec.charter.aircrafts.fragment.Component;
import flight.report.ec.charter.aircrafts.fragment.Document;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Keyboard;

public class AircraftTabs extends BaseActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;

    private Keyboard keyboard;

    private BdContructor bd;
    private Aircraft aircraftGlobal;
    public static final String AIRCRAFT_ID = "ID";
    public static final String PAGE = "PAGE";

    private Document documentFragment;
    private Component componentFragment;
    private Chat chatFragment;
    private int _fragmentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aircraft_tabs);
        this.bd = new BdContructor(AircraftTabs.this);
        this.keyboard = new Keyboard(AircraftTabs.this);

        enlazarVista();
        getAircraft(savedInstanceState);
        setToolbar(toolbar, aircraftGlobal.getTail(), true);
        setUpViewPager();
    }

    private void getAircraft(Bundle instance) {
        Bundle bundle = instance!=null ? instance : getIntent().getExtras();
        Long ID = bundle.getLong(AIRCRAFT_ID);
        aircraftGlobal = bd.aircraft.getAircraftById(ID);
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        toolbar = findViewById(R.id.toolbar);
        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
    }

    /**
     * Asignamos la configuracion del tab
     */
    private void setUpViewPager() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), setTabsContent()) {};
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
        setTabsText();

        /**
         * Ocultamos el teclado al pasar de tab
         */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                keyboard.hideKeyboard(getCurrentFocus());
            }

            @Override
            public void onPageSelected(int position) {
                _fragmentPage = position;
                keyboard.hideKeyboard(getCurrentFocus());
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    /**
     * Asignamos los nombres de los tabs
     */
    private void setTabsText() {
        int i = 0;
        for (String tab : getResources().getStringArray(R.array.aircraft_tabs)) {
            tablayout.getTabAt(i++).setText(tab);
        }
    }

    /**
     * Asignamos los fragment de cada tab
     */
    private ArrayList<Fragment> setTabsContent() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(configureDocument());
        fragments.add(configureComponent());
        fragments.add(configureChat());

        return fragments;
    }

    private Fragment configureDocument() {
        documentFragment = new Document();
        documentFragment.setAircraftGlobal(aircraftGlobal);
        return documentFragment;
    }

    private Fragment configureComponent() {
        componentFragment = new Component();
        componentFragment.setAircraftGlobal(aircraftGlobal);
        return componentFragment;
    }

    private Fragment configureChat() {
        chatFragment = new Chat();
        chatFragment.setAircraftGlobal(aircraftGlobal);
        return chatFragment;
    }

    /**
     * Guardamos la info, cuando se de vuelta a la pantalla
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putLong(AIRCRAFT_ID, aircraftGlobal.getId_web());
        currentState.putInt(PAGE, _fragmentPage);
    }

}
