
package pl.com.mantykora.kultrjmiasto.model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

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

}
