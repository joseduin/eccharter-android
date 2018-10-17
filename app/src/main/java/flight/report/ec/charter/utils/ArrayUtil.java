package flight.report.ec.charter.utils;

import java.util.ArrayList;

public class ArrayUtil {

    public static Object canGet(ArrayList<?> arr, int i) {
        try {
            return arr.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        } catch (IndexOutOfBoundsException ie) {
            return null;
        }
    }
}
