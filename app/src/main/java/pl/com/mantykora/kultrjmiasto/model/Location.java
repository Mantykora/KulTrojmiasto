
package pl.com.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location  implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subname")
    @Expose
    private String subname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }



    public Location() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.address, flags);
        dest.writeString(this.name);
        dest.writeString(this.subname);
    }

    protected Location(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.name = in.readString();
        this.subname = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
