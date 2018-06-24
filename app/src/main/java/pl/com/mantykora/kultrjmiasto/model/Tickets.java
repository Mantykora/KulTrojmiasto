
package pl.com.mantykora.kultrjmiasto.model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tickets {

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

}
