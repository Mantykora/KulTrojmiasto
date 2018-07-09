package pl.com.mantykora.kultrjmiasto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import pl.com.mantykora.kultrjmiasto.model.Attachment;
import pl.com.mantykora.kultrjmiasto.model.Event;
import pl.com.mantykora.kultrjmiasto.model.Location;
import pl.com.mantykora.kultrjmiasto.network.GetDataService;
import pl.com.mantykora.kultrjmiasto.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Icons_Fragment.OnIconSelectedListener{

    ProgressDialog progressDialog;
    Bundle locationBundle;
    List<Location> responseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Location>> locationCall = service.getAllLocations();


        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                Log.d("MainActivity", "" + response.body());

                responseList = response.body();
              //  locationBundle = new Bundle();
               // locationBundle.putSerializable("locationList", (Serializable) response.body());

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });

        Call<List<Event>> call = service.getAllEvents();

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                progressDialog.dismiss();
                Log.d("MainActivity", "" + response.body());

                Bundle bundle = new Bundle();
                bundle.putSerializable("eventList", (Serializable) response.body());
                bundle.putSerializable("locationList", (Serializable) responseList);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EventListFragment fragment = new EventListFragment();
                if (fragment.isAdded()) {

                } else {
                    fragment.setArguments(bundle);

                    fragmentTransaction.add(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (item.getItemId() == R.id.map_menu_item) {
           Intent intent = new Intent(MainActivity.this, MapsActivity.class);
           startActivity(intent);
           return true;

       } else return super.onOptionsItemSelected(item);
    }


    @Override
    public void onIconSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        EventListFragment eventListFragment = (EventListFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
        eventListFragment.updateArticleView(position);


    }
}
