package eu.mantykora.kultrjmiasto;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.mantykora.kultrjmiasto.model.CategoryEnum;
import eu.mantykora.kultrjmiasto.model.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {




    private ArrayList<Event> filteredList;



    private ArrayList<Event> eventList;
    ArrayList<CategoryEnum> selectedList;

    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }


    public ArrayList<Event> getFilteredList() {
        return filteredList;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public ArrayList<CategoryEnum> getSelectedList() {
        return selectedList;
    }


    public void setFilteredList(ArrayList<Event> filteredList) {
        this.filteredList = filteredList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }

    public void setSelectedList(ArrayList<CategoryEnum> selectedList) {
        this.selectedList = selectedList;
    }


}
