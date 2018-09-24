package eu.mantykora.kultrjmiasto;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import eu.mantykora.kultrjmiasto.AboutActivity;
import eu.mantykora.kultrjmiasto.EventListFragment;
import eu.mantykora.kultrjmiasto.FavoritesActivity;
import eu.mantykora.kultrjmiasto.IconsFragment;
import eu.mantykora.kultrjmiasto.MapsActivity;
import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.model.Event;
import eu.mantykora.kultrjmiasto.model.Location;
import eu.mantykora.kultrjmiasto.network.GetDataService;
import eu.mantykora.kultrjmiasto.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IconsFragment.OnIconSelectedListener {

    private ProgressDialog progressDialog;
    private boolean isStateSaved;
    private boolean isDialogShowed;
    private List<Location> locationList;
    private List<Event> eventList;
    private Menu menu;
    private Toolbar myToolbar;
    private Spinner spinner;
    private LinearLayout constraintLayout;
    private  PopupWindow popupWindow;
    private  View layout;
    private ListView listView;


    @Override
    protected void onPause() {
        super.onPause();
        isStateSaved = true;


        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        isStateSaved = false;
        if (isDialogShowed) {
            isDialogShowed = false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        progressDialog = new ProgressDialog(eu.mantykora.kultrjmiasto.MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Location>> locationCall = service.getAllLocations();


        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locationList = response.body();

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

            }
        });

        Call<List<Event>> call = service.getAllEvents();


        call.enqueue(new Callback<List<Event>>()

                     {
                         @Override
                         public void onResponse
                                 (Call<List<Event>> call, Response<List<Event>> response) {

                             if ((progressDialog != null) && progressDialog.isShowing()) {
                                 progressDialog.dismiss();
                             }
                             eventList = response.body();

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
                                 if (isStateSaved) {
                                     isDialogShowed = true;
                                 } else {
                                     fragmentTransaction.commit();

                                 }
                             } else {
                                 fragmentTransaction.detach(fragment);
                                 fragmentTransaction.add(R.id.fragment_container, fragment);
                                 fragmentTransaction.commit();
                             }

                         }

                         @Override
                         public void onFailure(Call<List<Event>> call, Throwable t) {
                             if(progressDialog != null) {
                                 progressDialog.dismiss();
                             }

                             Toast.makeText(eu.mantykora.kultrjmiasto.MainActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                         }
                     }

        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);




        //spinner = (Spinner) findViewById(R.id.popup_city);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Map<Integer, Event> map = new HashMap<>();
        ArrayList<Event> toMapsList = new ArrayList<>();




        switch (item.getItemId()) {
            case R.id.map_menu_item:
                Intent intent = new Intent(eu.mantykora.kultrjmiasto.MainActivity.this, MapsActivity.class);
                for (Event x : eventList) {
                    int placeId = x.getPlace().getId();
                    for (Location y : locationList) {
                        if (y.getId() == placeId) {

                            x.setLocation(y);


                        }


                    }

                    map.put(placeId, x);

                }


                toMapsList.addAll(map.values());

                Bundle bundle = new Bundle();
                bundle.putSerializable("eventList", toMapsList);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            case R.id.favorites_menu_item:

                Intent intent1 = new Intent(eu.mantykora.kultrjmiasto.MainActivity.this, FavoritesActivity.class);
                startActivity(intent1);
                return true;

            case R.id.about_menu_item:

                Intent aboutIntent = new Intent(eu.mantykora.kultrjmiasto.MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            case R.id.filter_menu_item:
//                PopupMenu popupMenu = new PopupMenu(MainActivity.this, myToolbar, Gravity.RIGHT);
//                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
//                Spinner spinner = (Spinner) popupMenu.getMenu().findItem(R.id.popup_city).getActionView();
//                SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.cities_array, android.R.layout.simple_spinner_dropdown_item);
//
//
//                spinner.setAdapter(spinnerAdapter);
              //  popupMenu.show();



                popupWindow = new PopupWindow(MainActivity.this);
                layout = getLayoutInflater().inflate(R.layout.popup_window, null);
                popupWindow.setContentView(layout);
        popupWindow.setHeight(1000);
        popupWindow.setWidth(500);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        popupWindow.showAtLocation(myToolbar, Gravity.CENTER, 0, 0);
//                    }
//                }, 4000);

//                listView = layout.findViewById(R.id.popup_window_city_spinner);
//
//                ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, R.array.cities_array);
//                listView.setAdapter(adapter);
                popupWindow.showAsDropDown(myToolbar, Gravity.CENTER, 0, 0);
//                Spinner spinner = layout.findViewById(R.id.popup_window_city_spinner);
//                SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.cities_array, android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(spinnerAdapter);
//
//                new Handler().postDelayed(new Runnable(){
//
//                    public void run() {
//                        popupWindow.showAsDropDown(myToolbar, Gravity.CENTER,0,0);
//                    }
//
//                }, 200L);


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
