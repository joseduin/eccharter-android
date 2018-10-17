package flight.report.ec.charter.utils;

import android.util.Log;

import java.util.Locale;

public class StringUtil {

    public static String toLowerCase(String s) {
        return s.toLowerCase(Locale.getDefault());
    }

    public static boolean contains(String s1, String s2) {
        s1 = toLowerCase(nullTranform(s1).trim());
        s2 = toLowerCase(nullTranform(s2).trim());
        return s1.contains(s2);
    }

    public static String nullTranform(String s) {
        return s == null ? "" : s;
    }

    public static String format(String s, int n) {
        return String.format(s, n);
    }

    public static boolean equals(String s1, String s2) {
        if (s1==null || s2==null) return false;

        s1 = s1.trim();
        s2 = s2.trim();
        return s1.equals(s2);
    }

    public static void console (String title, Object body) {
        Log.d(title, String.valueOf(body));
    }

    public static String removeJumps(String s) {
        s = s.trim();
        return s.replace("\n", "");
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
}
