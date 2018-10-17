package flight.report.ec.charter.modelo.components;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import flight.report.ec.charter.bd.ConstantesBaseDatos;

public class Chat implements Parcelable {
    private Long id;
    private Long aircraftId;
    private Long time;
    private String message;
    private String userName;
    private int type;
    private boolean send;
    private String src;
    private String srcSave;

    public Chat(Long id, Long aircraftId, Long time, String message, String userName, int type, String src) {
        this.id = id;
        this.aircraftId = aircraftId;
        this.time = time;
        this.message = message;
        this.userName = userName;
        this.type = type;
        this.src = src;
    }

    public Chat() {}

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrcSave() {
        return srcSave;
    }

    public void setSrcSave(String srcSave) {
        this.srcSave = srcSave;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public void clone(Chat chat) {
        this.aircraftId = chat.getAircraftId();
        this.time = chat.getTime();
        this.message = chat.getMessage();
        this.userName = chat.getUserName();
        this.type = chat.getType();
        this.src = chat.getSrc();
        this.send = chat.isSend();
    }

    public static Chat getChat(Cursor cursor) {
        Chat chat = new Chat();
        chat.setId(cursor.getLong(0));
        chat.setAircraftId(cursor.getLong(1));
        chat.setTime(cursor.getLong(2));
        chat.setMessage(cursor.getString(3));
        chat.setUserName(cursor.getString(4));
        chat.setType(cursor.getInt(5));
        chat.setSrc(cursor.getString(6));
        chat.setSrcSave(cursor.getString(7));
        chat.setSend(ConstantesBaseDatos.booleanConvert(cursor.getInt(8)));
        return chat;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public Chat(Parcel in) {
        this.id = in.readLong();
        this.aircraftId = in.readLong();
        this.time = in.readLong();
        this.message = in.readString();
        this.userName = in.readString();
        this.type = in.readInt();
        this.send = ConstantesBaseDatos.booleanConvert(in.readInt());
        this.src = in.readString();
        this.srcSave = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.aircraftId);
        dest.writeLong(this.time);
        dest.writeString(this.message);
        dest.writeString(this.userName);
        dest.writeInt(ConstantesBaseDatos.intergetConvert(this.send));
        dest.writeInt(this.type);
        dest.writeString(this.src);
        dest.writeString(this.srcSave);
    }
}
