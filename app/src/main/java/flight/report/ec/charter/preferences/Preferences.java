package flight.report.ec.charter.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public int MODE = 0;
    public String NAME = "AndroidHivePref";

    public String KEY_USERNAME = "key_user_name";

    private Context _context;

    public Preferences(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(NAME, MODE);
        editor = pref.edit();
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

}
