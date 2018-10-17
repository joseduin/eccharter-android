package flight.report.ec.charter.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Jose on 13/11/2017.
 */

public final class IrA {

    public static void vista(Context activityActual, Class<?> activityNuevo) {
        Intent i = new Intent(activityActual, activityNuevo);
        activityActual.startActivity(i);
    }

    public static void vista(Context activityActual, Class<?> activityNuevo, int ID) {
        Intent i = new Intent(activityActual, activityNuevo);
        i.putExtra("ID", ID);
        activityActual.startActivity(i);
    }

    public static void vista(Context activityActual, Class<?> activityNuevo, Long ID) {
        Intent i = new Intent(activityActual, activityNuevo);
        i.putExtra("ID", ID);
        activityActual.startActivity(i);
    }

    public static Intent vista(Context activityActual, Class<?> activityNuevo, int ID, int ID_REPORT) {
        Intent i = new Intent(activityActual, activityNuevo);
        i.putExtra("ID", ID);
        i.putExtra("ID_REPORT", ID_REPORT);
        return i;
    }

    public static void vistaAndClear(Context activityActual, Class<?> activityNuevo) {
        Intent i = new Intent(activityActual, activityNuevo);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activityActual.startActivity(i);
    }

}
