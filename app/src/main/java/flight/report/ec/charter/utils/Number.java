package flight.report.ec.charter.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by Jose on 23/1/2018.
 */

public final class Number {

    /**
     * Transformar y dar formato de Double a String
     */
    public static String decimalFormat(double value, int decimal) {
        return String.format("%." + decimal + "f", value).replace(",", ".");
    }

    /**
     * Input con solo numeros y decimales
     */
    public static InputFilter decimalEditText(final int decimal) {
        InputFilter filter = new InputFilter() {
            // Preguntar Maximo ponometro del hoob
            final int maxDigitsBeforeDecimalPoint = 5;
            final int maxDigitsAfterDecimalPoint = decimal;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;
            }
        };
        return filter;
    }
}
