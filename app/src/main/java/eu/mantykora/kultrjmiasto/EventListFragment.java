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
import android.widget.Toast;

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
    private boolean isThaterChecked;

    public EventListFragment() {
        super();
        setArguments(new Bundle());
    }


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

        if (adapter.getItemCount() == 0) {
            doveTv.setVisibility(View.VISIBLE);
            doveIv.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            iconPosition = savedInstanceState.getInt("iconPosition");

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("iconPosition", iconPosition);
    }




    public List<Event> getList() {
        if (litToShort != null) {
            return litToShort;
        }

        else {
            return null;
        }
    }


}
