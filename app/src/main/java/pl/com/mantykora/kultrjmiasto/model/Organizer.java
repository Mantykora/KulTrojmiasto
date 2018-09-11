
package pl.com.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organizer implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("designation")
    @Expose
    private String designation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.designation);
    }

    public Organizer() {
    }

    Organizer(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.designation = in.readString();
    }

    public static final Parcelable.Creator<Organizer> CREATOR = new Parcelable.Creator<Organizer>() {
        @Override
        public Organizer createFromParcel(Parcel source) {
            return new Organizer(source);
        }

        @Override
        public Organizer[] newArray(int size) {
            return new Organizer[size];
        }
    };
}
