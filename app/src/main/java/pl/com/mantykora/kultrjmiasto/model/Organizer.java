
package pl.com.mantykora.kultrjmiasto.model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organizer {

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

}
