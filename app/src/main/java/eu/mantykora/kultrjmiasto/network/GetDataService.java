package eu.mantykora.kultrjmiasto.network;

import java.util.List;

import eu.mantykora.kultrjmiasto.model.Event;
import eu.mantykora.kultrjmiasto.model.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    //https://medium.com/@prakash_pun/retrofit-a-simple-android-tutorial-48437e4e5a23 Retrofit tutorial I used

    @GET("/api/rest/events.json")
    Call<List<eu.mantykora.kultrjmiasto.model.Event>> getAllEvents();

    @GET("/api/rest/places.json")
    Call<List<eu.mantykora.kultrjmiasto.model.Location>> getAllLocations();


}
