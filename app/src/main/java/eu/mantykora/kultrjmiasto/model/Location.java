
package eu.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.mantykora.kultrjmiasto.model.Address;

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

    Location(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.name = in.readString();
        this.subname = in.readString();
    }

    public static final Creator<eu.mantykora.kultrjmiasto.model.Location> CREATOR = new Creator<eu.mantykora.kultrjmiasto.model.Location>() {
        @Override
        public eu.mantykora.kultrjmiasto.model.Location createFromParcel(Parcel source) {
            return new eu.mantykora.kultrjmiasto.model.Location(source);
        }

        @Override
        public eu.mantykora.kultrjmiasto.model.Location[] newArray(int size) {
            return new eu.mantykora.kultrjmiasto.model.Location[size];
        }
    };
}
