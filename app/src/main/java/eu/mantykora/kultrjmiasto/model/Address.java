
package eu.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    Address(Parcel in) {
        street = in.readString();
        zipcode = in.readString();
        city = in.readString();
        lat = in.readString();
        lng = in.readString();
    }

    public static final Creator<eu.mantykora.kultrjmiasto.model.Address> CREATOR = new Creator<eu.mantykora.kultrjmiasto.model.Address>() {
        @Override
        public eu.mantykora.kultrjmiasto.model.Address createFromParcel(Parcel in) {
            return new eu.mantykora.kultrjmiasto.model.Address(in);
        }

        @Override
        public eu.mantykora.kultrjmiasto.model.Address[] newArray(int size) {
            return new eu.mantykora.kultrjmiasto.model.Address[size];
        }
    };

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


    public Address() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(street);
        dest.writeString(zipcode);
        dest.writeString(city);
        dest.writeString(lat);
        dest.writeString(lng);
    }
}
