package flight.report.ec.charter.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import flight.report.ec.charter.R;

/**
 * Created by Jose on 19/1/2018.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener,
        TextView.OnEditorActionListener,
        TextWatcher {
    private Keyboard keyboard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.keyboard = new Keyboard(BaseActivity.this);
    }

    protected void setToolbar(Toolbar t, String title, boolean back) {
        setSupportActionBar(t);
        setToolbarTitle(title);
        if (back)
            setToolbarBackButton(true);
    }

    protected void setToolbarTitle(String t) {
        getSupportActionBar().setTitle(t);
    }

    protected void setToolbarBackButton(boolean b) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(b);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);
    }

    protected void onScrollListener(RecyclerView recicler) {
        recicler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                keyboard.hideKeyboard(getCurrentFocus());
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                keyboard.hideKeyboard(getCurrentFocus());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
