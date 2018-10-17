package flight.report.ec.charter.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Image implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    private HashMap<String, String> album;

    public Image(HashMap<String, String> album) {
        this.album = album;
    }

    public HashMap<String, String> getAlbum() {
        return album;
    }

    public void setAlbum(HashMap<String, String> album) {
        this.album = album;
    }

    // Parcelling part
    public Image(Parcel in){
        this.album = (HashMap<String, String>) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(album);
    }
}
