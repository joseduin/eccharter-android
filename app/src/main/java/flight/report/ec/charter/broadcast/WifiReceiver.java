package flight.report.ec.charter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.modelo.Send;
import flight.report.ec.charter.preferences.Preferences;
import flight.report.ec.charter.task.Server;
import flight.report.ec.charter.utils.Connection;

public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Connection connection = new Connection(context);
        BdContructor bd = new BdContructor(context);
        Preferences preferences = new Preferences(context);
        ArrayList<Report> reports = bd.report.reportPen();
        Send SEND = bd.send.sendById(1);

        if (connection.isOnline()) {
            if (SEND.isServer()) {
                for (Report report : reports) {
                    Server server = new Server(context, report, preferences);
                    server.send();

                    report.setPen(false);
                    bd.report.reportUpdate(report, "pen");
                }
            }
        }
    }
}
