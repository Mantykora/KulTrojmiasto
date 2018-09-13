
package eu.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attachment implements Parcelable {

    @SerializedName("fileName")
    @Expose
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fileName);
    }

    public Attachment() {
    }

    Attachment(Parcel in) {
        this.fileName = in.readString();
    }

    public static final Creator<eu.mantykora.kultrjmiasto.model.Attachment> CREATOR = new Creator<eu.mantykora.kultrjmiasto.model.Attachment>() {
        @Override
        public eu.mantykora.kultrjmiasto.model.Attachment createFromParcel(Parcel source) {
            return new eu.mantykora.kultrjmiasto.model.Attachment(source);
        }

        @Override
        public eu.mantykora.kultrjmiasto.model.Attachment[] newArray(int size) {
            return new eu.mantykora.kultrjmiasto.model.Attachment[size];
        }
    };
}
