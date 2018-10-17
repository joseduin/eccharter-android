package flight.report.ec.charter.modelo.components;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import flight.report.ec.charter.bd.ConstantesBaseDatos;

public class Document implements Parcelable {
    private Long id;
    private int masterType;
    private Long masterId;
    private Boolean is_img;
    private String title;
    private String src;
    private String srcSave;
    private String srcImgSave;

    public Document(int masterType, Long masterId, Boolean is_img, String title, String src) {
        this.masterType = masterType;
        this.masterId = masterId;
        this.is_img = is_img;
        this.title = title;
        this.src = src;
    }

    public Document() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMasterType() {
        return masterType;
    }

    public void setMasterType(int masterType) {
        this.masterType = masterType;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Boolean getIs_img() {
        return is_img;
    }

    public void setIs_img(Boolean is_img) {
        this.is_img = is_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSrcImgSave() {
        return srcImgSave;
    }

    public void setSrcImgSave(String srcImgSave) {
        this.srcImgSave = srcImgSave;
    }

    public void clone(Document document) {
        this.masterType = document.getMasterType();
        this.masterId = document.getMasterId();
        this.is_img = document.getIs_img();
        this.title = document.getTitle();
        this.src = document.getSrc();
        this.srcSave = document.getSrcSave();
        this.srcImgSave = document.getSrcImgSave();
    }

    public static Document getDocument(Cursor cursor) {
        Document document = new Document();
        document.setId(cursor.getLong(0));
        document.setMasterType(cursor.getInt(1));
        document.setMasterId(cursor.getLong(2));
        document.setIs_img(ConstantesBaseDatos.booleanConvert( cursor.getInt(3) ));
        document.setTitle(cursor.getString(4));
        document.setSrc(cursor.getString(5));
        document.setSrcSave(cursor.getString(6));
        document.setSrcImgSave(cursor.getString(7));
        return document;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public Document(Parcel in) {
        this.id = in.readLong();
        this.masterType = in.readInt();
        this.masterId = in.readLong();
        this.is_img = ConstantesBaseDatos.booleanConvert( in.readInt() );
        this.title = in.readString();
        this.src = in.readString();
        this.srcSave = in.readString();
        this.srcImgSave = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.masterType);
        dest.writeLong(this.masterId);
        dest.writeInt( ConstantesBaseDatos.intergetConvert(this.is_img) );
        dest.writeString(this.title);
        dest.writeString(this.src);
        dest.writeString(this.srcSave);
        dest.writeString(this.srcImgSave);
    }
}
