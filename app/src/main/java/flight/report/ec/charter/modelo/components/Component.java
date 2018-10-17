package flight.report.ec.charter.modelo.components;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Component implements Parcelable {
    private Long id;
    private Long aircraftId;
    private String drawing;
    private String item;
    private String position;
    private int status;

    public Component(Long aircraftId, String drawing, String item, String position, int status) {
        this.aircraftId = aircraftId;
        this.drawing = drawing;
        this.item = item;
        this.position = position;
        this.status = status;
    }

    public Component() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getDrawing() {
        return drawing;
    }

    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void clone(Component component) {
        this.aircraftId = component.getAircraftId();
        this.drawing = component.getDrawing();
        this.status = component.getStatus();
    }

    public static Component getComponent(Cursor cursor) {
        Component component = new Component();
        component.setId(cursor.getLong(0));
        component.setAircraftId(cursor.getLong(1));
        component.setDrawing(cursor.getString(2));
        component.setStatus(cursor.getInt(3));
        component.setItem(cursor.getString(4));
        component.setPosition(cursor.getString(5));

        return component;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        public Component[] newArray(int size) {
            return new Component[size];
        }
    };

    public Component(Parcel in) {
        this.aircraftId = in.readLong();
        this.drawing = in.readString();
        this.item = in.readString();
        this.position = in.readString();
        this.status = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.drawing);
        dest.writeString(this.item);
        dest.writeString(this.position);
        dest.writeInt(this.status);
    }
}
