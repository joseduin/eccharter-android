package flight.report.ec.charter.modelo.request;

import java.util.ArrayList;

import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;

/**
 * Created by Jose on 18/4/2018.
 */

public class ReportRequest {

    private String app = "android";
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
    private String username;
    private int id;
    private boolean sentMail;
    private boolean sentMailOpe;
    private boolean sentServer;

    private ArrayList<ExpensesReport> expenses = new ArrayList<>();
    private ArrayList<Aircraft> aircrafts = new ArrayList<>();
    private ArrayList<PlanRequest> plans = new ArrayList<>();

    public ReportRequest(String username, Report r, ArrayList<ExpensesReport> expenses, ArrayList<Aircraft> aircrafts, ArrayList<PlanRequest> plans) {
        this.username           = username;
        this.id                 = r.getId();
        this.customer           = r.getCustomer();
        this.passengers         = r.getPassengers();
        this.passengers_photo   = r.getPassengers_photo();
        this.aircraft           = r.getAircraft();
        this.capitan            = r.getCapitan();
        this.copilot            = r.getCopilot();
        this.date               = r.getDate();
        this.route              = r.getRoute();
        this.gps_flight_time    = r.getGps_flight_time();
        this.hour_initial       = r.getHour_initial();
        this.hour_final         = r.getHour_final();
        this.long_time          = r.getLong_time();
        this.remarks            = r.getRemarks();
        this.send               = r.isSend();
        this.engine1            = r.getEngine1();
        this.engine2            = r.getEngine2();
        this.comboEngine1       = r.getComboEngine1();
        this.comboEngine2       = r.getComboEngine2();
        this.cockpit            = r.isCockpit();
        this.sentMail           = r.isSentMail();
        this.sentMailOpe        = r.isSentMailOpe();
        this.sentServer         = r.isSentServer();
        this.expenses           = expenses;
        this.aircrafts          = aircrafts;
        this.plans              = plans;
    }
    public ReportRequest() {}

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

    public ArrayList<ExpensesReport> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<ExpensesReport> expenses) {
        this.expenses = expenses;
    }

    public ArrayList<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(ArrayList<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<PlanRequest> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<PlanRequest> plans) {
        this.plans = plans;
    }

    public boolean isSentMail() {
        return sentMail;
    }

    public void setSentMail(boolean sentMail) {
        this.sentMail = sentMail;
    }

    public boolean isSentMailOpe() {
        return sentMailOpe;
    }

    public void setSentMailOpe(boolean sentMailOpe) {
        this.sentMailOpe = sentMailOpe;
    }

    public boolean isSentServer() {
        return sentServer;
    }

    public void setSentServer(boolean sentServer) {
        this.sentServer = sentServer;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}

