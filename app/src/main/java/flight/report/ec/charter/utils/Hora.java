package flight.report.ec.charter.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jose on 20/1/2018.
 */
/**
 * Formatos de horas
 **/
public final class Hora {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.US);
    public static SimpleDateFormat sdfc = new SimpleDateFormat("dd-MMM-yy-HH-mm-ss", Locale.US);
    public static DateFormat server = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public static String today() {
        return sdf.format(new Date()).toUpperCase();
    }

    public static String time() {
        return sdfc.format(new Date());
    }

}
