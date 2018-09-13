
package eu.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public Place() {
    }

    Place(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Creator<eu.mantykora.kultrjmiasto.model.Place> CREATOR = new Creator<eu.mantykora.kultrjmiasto.model.Place>() {
        @Override
        public eu.mantykora.kultrjmiasto.model.Place createFromParcel(Parcel source) {
            return new eu.mantykora.kultrjmiasto.model.Place(source);
        }

        @Override
        public eu.mantykora.kultrjmiasto.model.Place[] newArray(int size) {
            return new eu.mantykora.kultrjmiasto.model.Place[size];
        }
    };
}
