package flight.report.ec.charter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {
    private Context context;

    public Connection(Context context) {
        this.context = context;
    }

    /**
     *  Buscamos si el dispositivo tiene acceso a internet [datos, wifi]
     **/
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
