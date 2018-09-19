package eu.mantykora.kultrjmiasto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.adapter.EventsAdapter;
import eu.mantykora.kultrjmiasto.model.Event;
import eu.mantykora.kultrjmiasto.model.Location;

public class EventListFragment extends Fragment {


    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private List<Event> eventList;
    private List<Event> litToShort;
    private int iconPosition;
    private ImageView doveIv;
    private TextView doveTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        Bundle bundle = this.getArguments();

        doveIv = view.findViewById(R.id.golab_iv);
        doveTv = view.findViewById(R.id.golab_tv);
        eventList = (List<Event>) bundle.getSerializable("eventList");
        List<Location> locationList = (List<Location>) bundle.getSerializable("locationList");

        recyclerView = view.findViewById(R.id.events_rv);
        adapter = new EventsAdapter(eventList, getActivity());

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float pixelValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 175, getResources().getDisplayMetrics());

        double spanCountDouble = Math.floor(metrics.widthPixels / pixelValue);
        int spanCount = (int) spanCountDouble;

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            iconPosition = savedInstanceState.getInt("iconPosition");
            updateArticleView(iconPosition);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("iconPosition", iconPosition);
    }

    public void updateArticleView(int position) {
        if (position == 0) {
            iconPosition = 0;
            cutListView(19);
        }
        if (position == 1) {
            iconPosition = 1;
            cutListView(1);
        }
        if (position == 2) {
            iconPosition = 2;
            cutListView(51);
        }
        if (position == 3) {
            iconPosition = 3;
            cutListView(35);
        }
        if (position == 4) {
            iconPosition = 4;
            cutListView(83);
        }
        if (position == 5) {
            iconPosition = 5;
            cutListView(61);
        }
        if (position == 6) {
            iconPosition = 6;
            cutListView(69);
        }
        if (position == 7) {
            iconPosition = 7;
            cutListView(77);
        }
        if (position == 8) {
            iconPosition = 8;
            cutListView(96);
        }
        if (position == 9) {
            iconPosition = 9;
            adapter = new EventsAdapter(eventList, getActivity());
            recyclerView.setAdapter(adapter);
        }

    }

    private void cutListView(int categoryId) {


        litToShort = new ArrayList<>(eventList);
        Iterator<Event> eventIterator = litToShort.iterator();
        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            if (event.getCategoryId() != categoryId) {
                eventIterator.remove();
            }
        }

        adapter = new EventsAdapter(litToShort, getActivity());
        if (adapter.getItemCount() == 0) {
            doveTv.setVisibility(View.VISIBLE);
            doveIv.setVisibility(View.VISIBLE);


        }
        recyclerView.setAdapter(adapter);


    }


}
