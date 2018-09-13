package eu.mantykora.kultrjmiasto.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorite")
public class FavoriteEntry implements Parcelable {

    @PrimaryKey
    private int id;
    private String title;
    private String place;
    private String startTicket;
    private String endTicket;
    private String date;
    private String description;
    private String link;
    private String image;
    private boolean isLiked;
    private String ticketType;
    private String shortDescription;


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public FavoriteEntry(int id, String title, String place, String startTicket, String endTicket, String date, String description, String link, String image, boolean isLiked, String ticketType, String shortDescription) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.startTicket = startTicket;
        this.endTicket = endTicket;
        this.date = date;
        this.description = description;
        this.link = link;
        this.image = image;
        this.isLiked = isLiked;
        this.ticketType = ticketType;
        this.shortDescription = shortDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.place);
        dest.writeString(this.startTicket);
        dest.writeString(this.endTicket);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.link);
        dest.writeString(this.image);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
        dest.writeString(this.ticketType);
        dest.writeString(this.shortDescription);
    }

    FavoriteEntry(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.place = in.readString();
        this.startTicket = in.readString();
        this.endTicket = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.image = in.readString();
        this.isLiked = in.readByte() != 0;
        this.ticketType = in.readString();
        this.shortDescription = in.readString();
    }

    public static final Creator<FavoriteEntry> CREATOR = new Creator<FavoriteEntry>() {
        @Override
        public FavoriteEntry createFromParcel(Parcel source) {
            return new FavoriteEntry(source);
        }

        @Override
        public FavoriteEntry[] newArray(int size) {
            return new FavoriteEntry[size];
        }
    };
}
