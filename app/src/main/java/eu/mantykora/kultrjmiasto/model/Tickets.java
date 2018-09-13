
package eu.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets implements Parcelable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("startTicket")
    @Expose
    private String startTicket;
    @SerializedName("endTicket")
    @Expose
    private String endTicket;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTicket() {
        return startTicket;
    }

    public void setStartTicket(String startTicket) {
        this.startTicket = startTicket;
    }

    public String getEndTicket() {
        return endTicket;
    }

    public void setEndTicket(String endTicket) {
        this.endTicket = endTicket;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.startTicket);
        dest.writeString(this.endTicket);
    }

    public Tickets() {
    }

    Tickets(Parcel in) {
        this.type = in.readString();
        this.startTicket = in.readString();
        this.endTicket = in.readString();
    }

    public static final Creator<eu.mantykora.kultrjmiasto.model.Tickets> CREATOR = new Creator<eu.mantykora.kultrjmiasto.model.Tickets>() {
        @Override
        public eu.mantykora.kultrjmiasto.model.Tickets createFromParcel(Parcel source) {
            return new eu.mantykora.kultrjmiasto.model.Tickets(source);
        }

        @Override
        public eu.mantykora.kultrjmiasto.model.Tickets[] newArray(int size) {
            return new eu.mantykora.kultrjmiasto.model.Tickets[size];
        }
    };
}
