
package pl.com.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls implements Parcelable {

    @SerializedName("www")
    @Expose
    private String www;
    @SerializedName("fb")
    @Expose
    private String fb;
    @SerializedName("tickets")
    @Expose
    private String tickets;

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.www);
        dest.writeString(this.fb);
        dest.writeString(this.tickets);
    }

    public Urls() {
    }

    Urls(Parcel in) {
        this.www = in.readString();
        this.fb = in.readString();
        this.tickets = in.readString();
    }

    public static final Parcelable.Creator<Urls> CREATOR = new Parcelable.Creator<Urls>() {
        @Override
        public Urls createFromParcel(Parcel source) {
            return new Urls(source);
        }

        @Override
        public Urls[] newArray(int size) {
            return new Urls[size];
        }
    };
}
