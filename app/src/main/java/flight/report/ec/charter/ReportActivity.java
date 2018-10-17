package flight.report.ec.charter;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import flight.report.ec.charter.adaptador.PagerAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.fragment.Basic;
import flight.report.ec.charter.fragment.Expenses;
import flight.report.ec.charter.fragment.Flight;
import flight.report.ec.charter.fragment.Plan;
import flight.report.ec.charter.fragment.Remarks;
import flight.report.ec.charter.fragment.Report;
import flight.report.ec.charter.utils.BaseActivity;
import flight.report.ec.charter.utils.Keyboard;
import flight.report.ec.charter.utils.StringUtil;

public class ReportActivity extends BaseActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private PagerAdapter pagerAdapter;
    private FloatingActionButton add_item;

    private BdContructor bd;
    private flight.report.ec.charter.modelo.Report reporteGlobal;
    public static final String REPORTE_ID = "report_id";

    private Expenses fragmentExpenses;
    private Report fragmentReport;
    private Plan fragmentPlan;
    private Remarks fragmentRemarks;
    private Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        keyboard = new Keyboard(ReportActivity.this);
        bd = new BdContructor(ReportActivity.this);

        loadReport();
        if (savedInstanceState != null) {
            int ID = savedInstanceState.getInt(REPORTE_ID);
            if (ID == 0) {
                reporteGlobal = bd.report.ultimoBorrador();
            } else {
                reporteGlobal = bd.report.reportById(ID);
            }
        }
        enlazarVista();
        setToolbar(toolbar, getResources().getString(R.string.report), true);
        setUpViewPager();
    }

    /**
     * Obtenemos el ID del reporte que pasamos por parametros,
     * lo buscamos en la bd
     */
    private void loadReport() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int ID = extras.getInt("ID");
            if (ID == 0) {
                reporteGlobal = bd.report.ultimoBorrador();
            } else {
                reporteGlobal = bd.report.reportById(ID);
            }
        }
    }

    /**
     * Enlazamos vista con controlador
     */
    private void enlazarVista() {
        toolbar         = findViewById(R.id.he);
        tablayout       = findViewById(R.id.tablayout);
        viewPager       = findViewById(R.id.viewPager);
        add_item        = findViewById(R.id.add_item);
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
                keyboard.hideKeyboard(getCurrentFocus());
                floatButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void floatButton(final int pos) {
        boolean display = pos==2 || pos==3 || pos==4 || pos==5;
        if (pos==2 || pos==3 || pos==4) {
            add_item.setImageResource(R.drawable.ic_add);
            add_item.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.colorAccent)));
        } else if (pos==5) {
            add_item.setImageResource(R.drawable.ic_send);
            add_item.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ReportActivity.this, R.color.colorPrimary)));
        }
        add_item.setVisibility(display ? View.VISIBLE : View.GONE);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos==2) {
                    fragmentExpenses.goToExpense(0);
                } else if (pos==3) {
                    fragmentReport.goToAircraft(0);
                } else if (pos==4) {
                    fragmentPlan.goToPlan(0);
                } else if (pos==5) {
                    fragmentRemarks.sendReport();
                }
            }
        });
    }

    /**
     * Asignamos los nombres de los tabs
     */
    private void setTabsText() {
        int i = 0;
        for (String tab : getResources().getStringArray(R.array.tabs)) {
            tablayout.getTabAt(i++).setText(tab);
        }
    }

    /**
     * Asignamos los fragment de cada tab
     */
    private ArrayList<Fragment> setTabsContent() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(configureBasic());
        fragments.add(configureFligth());
        fragments.add(configureExpense());
        fragments.add(configureReport());
        fragments.add(configurePlan());
        fragments.add(configureRemarks());

        return fragments;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configureReport() {
        fragmentReport = new Report();
        fragmentReport.setReporteGlobal(reporteGlobal);
        return fragmentReport;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configureExpense() {
        fragmentExpenses = new Expenses();
        fragmentExpenses.setReporteGlobal(reporteGlobal);
        return fragmentExpenses;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configureBasic() {
        Basic basic = new Basic();
        basic.setReporteGlobal(reporteGlobal);
        return basic;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configureFligth() {
        Flight flight = new Flight();
        flight.setReporteGlobal(reporteGlobal);
        return flight;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configurePlan() {
        fragmentPlan = new Plan();
        fragmentPlan.setReporteGlobal(reporteGlobal);
        return fragmentPlan;
    }

    /**
     * Le pasamos el reporte que acabamos de buscar al fragmente
     */
    private Fragment configureRemarks() {
        fragmentRemarks = new Remarks();
        fragmentRemarks.setReporteGlobal(reporteGlobal);
        return fragmentRemarks;
    }

    /**
     * Guardamos la info, cuando se de vuelta a la pantalla
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putInt(REPORTE_ID, reporteGlobal.getId());
    }

}
