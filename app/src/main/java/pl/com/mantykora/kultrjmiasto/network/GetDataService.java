package pl.com.mantykora.kultrjmiasto.network;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.model.Attachment;
import pl.com.mantykora.kultrjmiasto.model.Event;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    //https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23 Retrofit tutorial I used

    @GET("/api/rest/events.json")
    Call<List<Event>> getAllEvents();


}
