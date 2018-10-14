package eu.mantykora.kultrjmiasto;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.gu.toolargetool.TooLargeTool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import eu.mantykora.kultrjmiasto.model.CategoryEnum;
import eu.mantykora.kultrjmiasto.model.Event;
import eu.mantykora.kultrjmiasto.model.Location;
import eu.mantykora.kultrjmiasto.network.GetDataService;
import eu.mantykora.kultrjmiasto.network.RetrofitClientInstance;
import eu.mantykora.kultrjmiasto.utils.FilterUtils;
import eu.mantykora.kultrjmiasto.utils.PreferencesManager;
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
    private PopupWindow popupWindow;
    private View layout;
    private ListView listView;

   private CheckBox gdanskChB;
    private CheckBox sopotChB;
    private CheckBox gdyniaChB;
    private CheckBox otherChB;

    private Switch calendarSwitch;
    private DatePicker datePicker;

    EventListFragment fragment;
    FragmentTransaction fragmentTransaction;

    private ArrayList<Event> gdanskList;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentDate;

    ArrayList<Event> toFilterList;

    private boolean gdanskCheckboxChecked;
    private boolean gdyniaCheckboxChecked;
    private boolean sopotCheckboxChecked;
    private boolean otherCheckboxChecked;
    private boolean calendarSwitchChecked;

    private Map<Integer, Event> map;

    private ArrayList<Event> listFromCategories;

    private int iconPosition;
    private int categoryId;

    private Set<CategoryEnum> selectedCategories = new HashSet<>();

    private List<Event> filteredList;

    private AdView mAdView;

    private DataFragment dataFragment;
    ArrayList<CategoryEnum> selectedList;

    private IconsFragment iconsFragment;

    @Override
    protected void onDestroy() {
        super.onDestroy();



       selectedList = new ArrayList<>(selectedCategories);


        dataFragment.setEventList((ArrayList<Event>) eventList);
        dataFragment.setFilteredList((ArrayList<Event>) filteredList);
        dataFragment.setSelectedList((ArrayList<CategoryEnum>) selectedList);
    }



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

    private volatile boolean locationsLoaded = false;
    private volatile boolean eventsLoaded = false;

    private synchronized void dataLoaded() {
        if(eventsLoaded && locationsLoaded) {
            if(this.eventList != null && this.locationList != null) {
                for (Event x : eventList) {
                    int placeId = x.getPlace().getId();
                    for (Location y : locationList) {
                        if (y.getId() == placeId) {
                            x.setLocation(y);
                        }
                    }

                    map.put(placeId, x);
                }


                if ((progressDialog != null) && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("eventList", (Serializable) filterEventsBasedOnSharedPreferences(selectedCategories));
                bundle.putSerializable("locationList", (Serializable) locationList);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment eventFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                fragment = new EventListFragment();
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

            } else {
                // handle this case
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(eu.mantykora.kultrjmiasto.MainActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TooLargeTool.startLogging(getApplication());


      //  MobileAds.initialize(this, getResources().getString(R.string.add_id));


//
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();


        if (iconsFragment == null) {
            iconsFragment = new IconsFragment();

            fragmentTransaction3.add(R.id.fragment_icons, iconsFragment, "icons");
            fragmentTransaction3.commit();


   }

        dataFragment = (DataFragment) fragmentManager.findFragmentByTag("data");

        if (dataFragment == null) {
            dataFragment = new DataFragment();
            fragmentTransaction3.add(dataFragment, "data");


            }else {



                eventList = dataFragment.getEventList();
                filteredList = dataFragment.getFilteredList();
                selectedList =  dataFragment.getSelectedList();


                selectedCategories = new HashSet<>(selectedList);


                buildEventListFragmentFromIconFragment(selectedCategories);


        }



        progressDialog = new ProgressDialog(eu.mantykora.kultrjmiasto.MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<Location>> locationCall = service.getAllLocations();
        locationCall.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locationList = response.body();
                locationsLoaded = true;
                dataLoaded();
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                locationsLoaded = true;
                dataLoaded();
            }
        });

        Call<List<Event>> call = service.getAllEvents();
        call.enqueue(new Callback<List<Event>>()
                     {
                         @Override
                         public void onResponse
                                 (Call<List<Event>> call, Response<List<Event>> response) {
                             eventList = response.body();
                             eventsLoaded = true;
                             dataLoaded();
                         }

                         @Override
                         public void onFailure(Call<List<Event>> call, Throwable t) {
                             eventsLoaded = true;
                             dataLoaded();
                         }
                     }

        );

        map = new HashMap<>();
        popupWindow = new PopupWindow(MainActivity.this);
        layout = getLayoutInflater().inflate(R.layout.popup_window, null);
        gdanskChB = layout.findViewById(R.id.popup_gdansk_chb);
        gdyniaChB = layout.findViewById(R.id.popup_Gdynia_chb);
        sopotChB = layout.findViewById(R.id.popup_sopot_chb);
        otherChB = layout.findViewById(R.id.popup_other_chb);
        calendarSwitch = layout.findViewById(R.id.popup_switch);
        datePicker = layout.findViewById(R.id.popup_calendar);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ArrayList<Event> toMapsList = new ArrayList<>();


        switch (item.getItemId()) {
            case R.id.map_menu_item:
                Intent intent = new Intent(eu.mantykora.kultrjmiasto.MainActivity.this, MapsActivity.class);

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

                gdanskCheckboxChecked = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("checkBoxGdansk", false);
                gdanskChB.setChecked(gdanskCheckboxChecked);

                gdyniaCheckboxChecked = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("checkBoxGdynia", false);
                gdyniaChB.setChecked(gdyniaCheckboxChecked);

                sopotCheckboxChecked = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("checkBoxSopot", false);
                sopotChB.setChecked(sopotCheckboxChecked);

                otherCheckboxChecked = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("checkBoxOther", false);
                otherChB.setChecked(otherCheckboxChecked);

                calendarSwitchChecked = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("calendarSwitch", false);
                calendarSwitch.setChecked(calendarSwitchChecked);
                if (calendarSwitchChecked) {
                    datePicker.setVisibility(View.VISIBLE);


                    java.util.Calendar calendar = java.util.Calendar.getInstance();

                    currentYear = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("year", calendar.get(Calendar.YEAR));
                    currentMonth = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("month", calendar.get(Calendar.MONTH));
                    currentDay = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("day", calendar.get(Calendar.DAY_OF_MONTH));
                    saveDateInPreferences();

                    buildCurrentDate();
                    buildEventListFragmentFromFilterFragment();

                    datePicker.init(currentYear, currentMonth, currentDay, new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            currentYear = year;
                            currentMonth = monthOfYear;
                            currentDay = dayOfMonth;
                            saveDateInPreferences();
                            buildCurrentDate();

                            buildEventListFragmentFromFilterFragment();
                        }
                    });
                }


                popupWindow.setContentView(layout);
                popupWindow.setWidth(500);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);


                View.OnClickListener listener =
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                buildEventListFragmentFromFilterFragment();
                                switch (v.getId()) {
                                    case R.id.popup_gdansk_chb:
                                        gdanskCheckboxChecked = gdanskChB.isChecked();
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("checkBoxGdansk", gdanskCheckboxChecked).commit();
                                        break;
                                    case R.id.popup_Gdynia_chb:
                                        gdyniaCheckboxChecked = gdyniaChB.isChecked();
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("checkBoxGdynia", gdyniaCheckboxChecked).commit();
                                        break;
                                    case R.id.popup_sopot_chb:
                                        sopotCheckboxChecked = sopotChB.isChecked();
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("checkBoxSopot", sopotCheckboxChecked).commit();

                                        break;
                                    case R.id.popup_other_chb:
                                        otherCheckboxChecked = otherChB.isChecked();
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("checkBoxOther", otherCheckboxChecked).commit();

                                        break;


                                }
                            }
                        };

                gdanskChB.setOnClickListener(listener);
                gdyniaChB.setOnClickListener(listener);
                sopotChB.setOnClickListener(listener);
                otherChB.setOnClickListener(listener);
                calendarSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (calendarSwitch.isChecked()) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("calendarSwitch", true).commit();

                            setCalendar();
                        } else {
                            datePicker.setVisibility(View.GONE);
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("calendarSwitch", false).commit();

                        }

                        buildEventListFragmentFromFilterFragment();
                    }
                });


                popupWindow.showAsDropDown(myToolbar, Gravity.CENTER, 0, 0);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveDateInPreferences() {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("year", currentYear).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("month", currentMonth).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("day", currentDay).commit();
    }

    private void setCalendar() {
        datePicker.setVisibility(View.VISIBLE);


        java.util.Calendar calendar = java.util.Calendar.getInstance();

        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        saveDateInPreferences();
        buildCurrentDate();

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentYear = year;
                currentMonth = monthOfYear;
                currentDay = dayOfMonth;
                saveDateInPreferences();
                buildCurrentDate();

                buildEventListFragmentFromFilterFragment();
            }
        });
    }

    private void buildCurrentDate() {
        int monthValue = currentMonth + 1;
        String month = monthValue < 10 ? "0" + monthValue : Integer.toString(monthValue);
        String day = currentDay < 10 ? "0" + currentDay : Integer.toString(currentDay);
        currentDate = currentYear + "-" + month + "-" + day;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

       ArrayList<CategoryEnum> selectedList = (ArrayList<CategoryEnum>) savedInstanceState.getSerializable("selectedCategories");
        selectedCategories =  new HashSet<>(selectedList);

       IconsFragment iconsFragment = (IconsFragment) getFragmentManager().findFragmentById(R.id.fragment_icons);


        for (CategoryEnum categoryEnum: selectedCategories) {

            iconsFragment.enableColor(categoryEnum.getPosition());
        }

    }
//
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();

            ArrayList<CategoryEnum> selectedList = new ArrayList<>(selectedCategories);
            outState.putSerializable("selectedCategories", selectedList);

    }

    private void buildEventListFragmentFromFilterFragment() {
        FilterUtils.FilterInput filterInput =
                new FilterUtils.FilterInput(
                        new FilterUtils.FilterInput.CheckboxesState(
                                gdanskChB.isChecked(),
                                gdyniaChB.isChecked(),
                                sopotChB.isChecked(),
                                otherChB.isChecked(),
                                calendarSwitch.isChecked()
                        ),
                        new FilterUtils.FilterInput.FilterDate(
                                currentYear, currentMonth, currentDay
                        ),
                        selectedCategories
                );

        filteredList = FilterUtils.filterEvents(eventList, filterInput);

        replaceEventListFragment(filteredList);
    }

    private void buildEventListFragmentFromIconFragment(Set<CategoryEnum> selectedCategories) {
       filteredList = filterEventsBasedOnSharedPreferences(selectedCategories);

        replaceEventListFragment(filteredList);
    }

    @NonNull
    private List<Event> filterEventsBasedOnSharedPreferences(Set<CategoryEnum> selectedCategories) {
        FilterUtils.FilterInput filterInput =
                new FilterUtils.FilterInput(
                        PreferencesManager.getCheckboxesState(getApplicationContext()),
                        PreferencesManager.getFilterDate(getApplicationContext()),
                        selectedCategories == null ? new HashSet<>() : selectedCategories
                );

        return FilterUtils.filterEvents(eventList, filterInput);
    }

    private void replaceEventListFragment(List<Event> filteredList) {
        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("eventList",(Serializable) filteredList);

        fragment = new EventListFragment();
        fragment.setArguments(bundle1);

        fragmentTransaction1.replace(R.id.fragment_container, fragment);
        fragmentTransaction1.commit();
    }

    @Override
    public void onIconSelected(int position) {
        eu.mantykora.kultrjmiasto.model.CategoryEnum enumValue = null;
        if (position != 9) {
            iconPosition = position;
            enumValue = eu.mantykora.kultrjmiasto.model.CategoryEnum.forPosition(position);
            categoryId = enumValue.getCode();
        }
        IconsFragment iconsFragment = (IconsFragment) getFragmentManager().findFragmentById(R.id.fragment_icons);
        if(position == 9) {
            if(selectedCategories.isEmpty()) {
                selectedCategories.addAll(CategoryEnum.enumValues);
                for (CategoryEnum categoryEnum: selectedCategories) {
                    iconsFragment.enableColor(categoryEnum.getPosition());
                }
            } else {
                selectedCategories.clear();
                for (CategoryEnum categoryEnum: CategoryEnum.enumValues) {
                    iconsFragment.disableColor(categoryEnum.getPosition());
                }
            }
        } else {
            if (selectedCategories.contains(enumValue)) {
                selectedCategories.remove(enumValue);
            } else {
                selectedCategories.add(enumValue);
            }

            iconsFragment.colorGrid(position, enumValue);
        }

        buildEventListFragmentFromIconFragment(selectedCategories);

    }
}



