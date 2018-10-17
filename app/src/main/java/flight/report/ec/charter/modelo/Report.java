package flight.report.ec.charter.modelo;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import flight.report.ec.charter.bd.ConstantesBaseDatos;

/**
 * Created by Jose on 22/1/2018.
 */

public class Report implements Parcelable {
    private int id;
    private String customer;
    private String passengers;
    private String passengers_photo;
    private String aircraft;
    private String capitan;
    private String copilot;
    private String date;
    private String route;
    private double gps_flight_time;
    private double hour_initial;
    private double hour_final;
    private double long_time;
    private String remarks;
    private boolean send;
    private double engine1;
    private double engine2;
    private String comboEngine1;
    private String comboEngine2;
    private boolean cockpit;
    private boolean pen;
    private boolean sentMail;
    private boolean sentMailOpe;
    private boolean sentServer;

    public Report(int id, String customer, String passengers, String passengers_photo,
                  String aircraft, String capitan, String copilot, String date, String route,
                  double gps_flight_time, double hour_initial, double hour_final, double long_time,
                  String remarks, boolean send, double engine1, double engine2, String comboEngine1,
                  String comboEngine2, boolean cockpit, boolean pen) {
        this.id = id;
        this.customer = customer;
        this.passengers = passengers;
        this.passengers_photo = passengers_photo;
        this.aircraft = aircraft;
        this.capitan = capitan;
        this.copilot = copilot;
        this.date = date;
        this.route = route;
        this.gps_flight_time = gps_flight_time;
        this.hour_initial = hour_initial;
        this.hour_final = hour_final;
        this.long_time = long_time;
        this.remarks = remarks;
        this.send = send;
        this.engine1 = engine1;
        this.engine2 = engine2;
        this.comboEngine1 = comboEngine1;
        this.comboEngine2 = comboEngine2;
        this.cockpit = cockpit;
        this.pen = pen;
    }
    public Report() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getPassengers_photo() {
        return passengers_photo;
    }

    public void setPassengers_photo(String passengers_photo) {
        this.passengers_photo = passengers_photo;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getCapitan() {
        return capitan;
    }

    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    public String getCopilot() {
        return copilot;
    }

    public void setCopilot(String copilot) {
        this.copilot = copilot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public double getGps_flight_time() {
        return gps_flight_time;
    }

    public void setGps_flight_time(double gps_flight_time) {
        this.gps_flight_time = gps_flight_time;
    }

    public double getHour_initial() {
        return hour_initial;
    }

    public void setHour_initial(double hour_initial) {
        this.hour_initial = hour_initial;
    }

    public double getHour_final() {
        return hour_final;
    }

    public void setHour_final(double hour_final) {
        this.hour_final = hour_final;
    }

    public double getLong_time() {
        return long_time;
    }

    public void setLong_time(double long_time) {
        this.long_time = long_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public double getEngine1() {
        return engine1;
    }

    public void setEngine1(double engine1) {
        this.engine1 = engine1;
    }

    public double getEngine2() {
        return engine2;
    }

    public void setEngine2(double engine2) {
        this.engine2 = engine2;
    }

    public String getComboEngine1() {
        return comboEngine1;
    }

    public void setComboEngine1(String comboEngine1) {
        this.comboEngine1 = comboEngine1;
    }

    public String getComboEngine2() {
        return comboEngine2;
    }

    public void setComboEngine2(String comboEngine2) {
        this.comboEngine2 = comboEngine2;
    }

    public boolean isCockpit() {
        return cockpit;
    }

    public void setCockpit(boolean cockpit) {
        this.cockpit = cockpit;
    }

    public boolean isPen() {
        return pen;
    }

    public void setPen(boolean pen) {
        this.pen = pen;
    }

    public boolean isSentMail() {
        return sentMail;
    }

    public void setSentMail(boolean sentMail) {
        this.sentMail = sentMail;
    }

    public boolean isSentServer() {
        return sentServer;
    }

    public void setSentServer(boolean sentServer) {
        this.sentServer = sentServer;
    }

    public boolean isSentMailOpe() {
        return sentMailOpe;
    }

    public void setSentMailOpe(boolean sentMailOpe) {
        this.sentMailOpe = sentMailOpe;
    }

    public static Report getReport(Cursor cursors) {
        Report report = new Report();

        report.setId(cursors.getInt(0));
        report.setCustomer(cursors.getString(1));
        report.setPassengers(cursors.getString(2));
        report.setPassengers_photo(cursors.getString(3));
        report.setAircraft(cursors.getString(4));
        report.setCapitan(cursors.getString(5));
        report.setCopilot(cursors.getString(6));
        report.setDate(cursors.getString(7));
        report.setRoute(cursors.getString(8));
        report.setGps_flight_time(cursors.getDouble(9));
        report.setHour_initial(cursors.getDouble(10));
        report.setHour_final(cursors.getDouble(11));
        report.setLong_time(cursors.getDouble(12));
        report.setRemarks(cursors.getString(13));
        report.setSend( ConstantesBaseDatos.booleanConvert( cursors.getInt(14) ) );
        report.setEngine1(cursors.getDouble(15));
        report.setEngine2(cursors.getDouble(16));
        report.setComboEngine1(cursors.getString(17));
        report.setComboEngine2(cursors.getString(18));
        report.setCockpit( ConstantesBaseDatos.booleanConvert( cursors.getInt(19) ) );
        report.setPen(ConstantesBaseDatos.booleanConvert(cursors.getInt(20)));
        report.setSentMail(ConstantesBaseDatos.booleanConvert(cursors.getInt(21)));
        report.setSentServer(ConstantesBaseDatos.booleanConvert(cursors.getInt(22)));
        report.setSentMailOpe(ConstantesBaseDatos.booleanConvert(cursors.getInt(23)));

        return report;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    public Report(Parcel in) {
        this.id = in.readInt();
        this.customer = in.readString();
        this.passengers = in.readString();
        this.passengers_photo = in.readString();
        this.aircraft = in.readString();
        this.capitan = in.readString();
        this.copilot = in.readString();
        this.date = in.readString();
        this.route = in.readString();
        this.gps_flight_time = in.readDouble();
        this.hour_initial = in.readDouble();
        this.hour_final = in.readDouble();
        this.long_time = in.readDouble();
        this.remarks = in.readString();
        this.send = ConstantesBaseDatos.booleanConvert(in.readInt());
        this.engine1 = in.readDouble();
        this.engine2 = in.readDouble();
        this.comboEngine1 = in.readString();
        this.comboEngine2 = in.readString();
        this.cockpit = ConstantesBaseDatos.booleanConvert(in.readInt());
        this.pen = ConstantesBaseDatos.booleanConvert(in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.customer);
        dest.writeString(this.passengers);
        dest.writeString(this.passengers_photo);
        dest.writeString(this.aircraft);
        dest.writeString(this.capitan);
        dest.writeString(this.copilot);
        dest.writeString(this.date);
        dest.writeString(this.route);
        dest.writeDouble(this.gps_flight_time);
        dest.writeDouble(this.hour_initial);
        dest.writeDouble(this.hour_final);
        dest.writeDouble(this.long_time);
        dest.writeString(this.remarks);
        dest.writeInt(ConstantesBaseDatos.intergetConvert(this.send));
        dest.writeDouble(this.engine1);
        dest.writeDouble(this.engine2);
        dest.writeString(this.comboEngine1);
        dest.writeString(this.comboEngine2);
        dest.writeInt(ConstantesBaseDatos.intergetConvert(this.cockpit));
        dest.writeInt(ConstantesBaseDatos.intergetConvert(this.pen));
    }
}
