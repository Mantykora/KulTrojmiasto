package pl.com.mantykora.kultrjmiasto.network;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.model.Event;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/api/rest/events.json")
    Call<List<Event>> getAllEvents();
}
