
package pl.com.mantykora.kultrjmiasto.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("place")
    @Expose
    private Place place;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;
    @SerializedName("descLong")
    @Expose
    private String descLong;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("organizer")
    @Expose
    private Organizer organizer;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("tickets")
    @Expose
    private Tickets tickets;
    @SerializedName("descShort")
    @Expose
    private String descShort;
    @SerializedName("location")
    @Expose
    private Location location;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getDescLong() {
        return descLong;
    }

    public void setDescLong(String descLong) {
        this.descLong = descLong;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Tickets getTickets() {
        return tickets;
    }

    public void setTickets(Tickets tickets) {
        this.tickets = tickets;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.place, flags);
        dest.writeString(this.endDate);
        dest.writeString(this.name);
        dest.writeParcelable(this.urls, flags);
              dest.writeList(this.attachments);
        dest.writeString(this.descLong);
        dest.writeValue(this.categoryId);
        dest.writeString(this.startDate);
        dest.writeParcelable(this.organizer, flags);
        dest.writeValue(this.active);
        dest.writeParcelable(this.tickets, flags);
        dest.writeString(this.descShort);
        dest.writeParcelable(this.location, flags);
    }

    public Event() {
    }

    private Event(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.endDate = in.readString();
        this.name = in.readString();
        this.urls = in.readParcelable(Urls.class.getClassLoader());
        this.attachments = new ArrayList<>();
        in.readList(this.attachments, Attachment.class.getClassLoader());
        this.descLong = in.readString();
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startDate = in.readString();
        this.organizer = in.readParcelable(Organizer.class.getClassLoader());
        this.active = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tickets = in.readParcelable(Tickets.class.getClassLoader());
        this.descShort = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
