package flight.report.ec.charter.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Keyboard {
    private Context context;

    public Keyboard(Context context) {
        this.context = context;
    }

    /**
     * Ocultamos el teclado
     */
    public void hideKeyboard(View viewFocus) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = viewFocus;
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
