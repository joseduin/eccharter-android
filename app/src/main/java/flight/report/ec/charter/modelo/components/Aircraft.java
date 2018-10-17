package flight.report.ec.charter.modelo.components;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Aircraft implements Parcelable {
    private Long id;
    private Long id_web;
    private String tail;
    private String brand;
    private String model;
    private int componentGood;
    private int componentMedium;
    private int componentAlert;

    public Aircraft(Long id, String tail, String brand, String model) {
        this.id = id;
        this.tail = tail;
        this.brand = brand;
        this.model = model;
    }

    public Aircraft() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getComponentGood() {
        return componentGood;
    }

    public void setComponentGood(int componentGood) {
        this.componentGood = componentGood;
    }

    public int getComponentMedium() {
        return componentMedium;
    }

    public void setComponentMedium(int componentMedium) {
        this.componentMedium = componentMedium;
    }

    public int getComponentAlert() {
        return componentAlert;
    }

    public void setComponentAlert(int componentAlert) {
        this.componentAlert = componentAlert;
    }

    public Long getId_web() {
        return id_web;
    }

    public void setId_web(Long id_web) {
        this.id_web = id_web;
    }

    public Aircraft clone() {
        Aircraft aircraft = new Aircraft();
        aircraft.setTail(this.tail);
        aircraft.setBrand(this.brand);
        aircraft.setModel(this.model);
        aircraft.setComponentGood(this.componentGood);
        aircraft.setComponentMedium(this.componentMedium);
        aircraft.setComponentAlert(this.componentAlert);
        return aircraft;
    }

    public void clone(Aircraft aircraft) {
        this.id_web = aircraft.getId_web();
        this.tail = aircraft.getTail();
        this.brand = aircraft.getBrand();
        this.model = aircraft.getModel();
        this.componentGood = aircraft.getComponentGood();
        this.componentMedium = aircraft.getComponentMedium();
        this.componentAlert = aircraft.getComponentAlert();
    }

    public static Aircraft getAircrafts(Cursor cursor) {
        Aircraft aircraft = new Aircraft();
        aircraft.setId(cursor.getLong(0));
        aircraft.setTail(cursor.getString(1));
        aircraft.setBrand(cursor.getString(2));
        aircraft.setModel(cursor.getString(3));
        aircraft.setComponentGood(cursor.getInt(4));
        aircraft.setComponentMedium(cursor.getInt(5));
        aircraft.setComponentAlert(cursor.getInt(6));
        aircraft.setId_web(Long.valueOf(cursor.getString(7)));

        return aircraft;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Aircraft createFromParcel(Parcel in) {
            return new Aircraft(in);
        }

        public Aircraft[] newArray(int size) {
            return new Aircraft[size];
        }
    };

    public Aircraft(Parcel in){
        this.id = in.readLong();
        this.tail = in.readString();
        this.brand = in.readString();
        this.model = in.readString();
        this.componentGood = in.readInt();
        this.componentMedium = in.readInt();
        this.componentAlert = in.readInt();
        this.id_web = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.tail);
        dest.writeString(this.brand);
        dest.writeString(this.model);
        dest.writeInt(this.componentGood);
        dest.writeInt(this.componentMedium);
        dest.writeInt(this.componentAlert);
        dest.writeLong(this.id_web);
    }

}
