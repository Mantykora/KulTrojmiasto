package pl.com.mantykora.kultrjmiasto;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.com.mantykora.kultrjmiasto.model.Event;
import pl.com.mantykora.kultrjmiasto.model.Location;
import pl.com.mantykora.kultrjmiasto.network.GetDataService;
import pl.com.mantykora.kultrjmiasto.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IconsFragment.OnIconSelectedListener {

    ProgressDialog progressDialog;
    Bundle locationBundle;
    List<Location> locationList;
    List<Event> eventList;
    ArrayList<Event> newList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Location>> locationCall = service.getAllLocations();


        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                Log.d("MainActivity", "" + response.body());

                locationList = response.body();

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });

        Call<List<Event>> call = service.getAllEvents();


        class ResponseTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                call.enqueue(new Callback<List<Event>>()

                             {
                                 @Override
                                 public void onResponse
                                         (Call<List<Event>> call, Response<List<Event>> response) {
                                     progressDialog.dismiss();
                                     eventList = response.body();


                                     Log.d("MainActivity", "" + response.body());

                                     Bundle bundle = new Bundle();
                                     bundle.putSerializable("eventList", (Serializable) eventList);
                                     bundle.putSerializable("locationList", (Serializable) locationList);

                                     FragmentManager fragmentManager = getFragmentManager();
                                     FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                     Fragment eventFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                                     EventListFragment fragment = new EventListFragment();
                                     if (eventFragment == null) {
                                         fragment.setArguments(bundle);

                                         fragmentTransaction.add(R.id.fragment_container, fragment);
                                         fragmentTransaction.commit();
                                     } else {

                                     }

                                 }

                                 @Override
                                 public void onFailure(Call<List<Event>> call, Throwable t) {
                                     progressDialog.dismiss();
                                     Toast.makeText(MainActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                                 }
                             }

                );
                return null;
            }


        }

        new ResponseTask().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_menu_item:
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                for (Event x : eventList) {
                    int placeId = x.getPlace().getId();
                    for (Location y : locationList) {
                        if (y.getId() == placeId) {

                            x.setLocation(y);

                            Log.d("MainActivity", x.getLocation().getName());

                        }


                    }


                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("eventList", (Serializable) eventList);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            case R.id.favorites_menu_item:

                Intent intent1 = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onIconSelected(int position) {
        EventListFragment eventListFragment = (EventListFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
        eventListFragment.updateArticleView(position);


    }
}
