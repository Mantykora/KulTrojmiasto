package pl.com.mantykora.kultrjmiasto;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.List;

import pl.com.mantykora.kultrjmiasto.adapter.EventsAdapter;
import pl.com.mantykora.kultrjmiasto.model.Attachment;
import pl.com.mantykora.kultrjmiasto.model.Event;

public class EventListFragment extends Fragment {



    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private List<Event> eventList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        Bundle bundle = this.getArguments();
        eventList = (List<Event>) bundle.getSerializable("eventList");


        Log.d("EventsListFragment.java", "" + eventList);

        recyclerView = view.findViewById(R.id.events_rv);
        adapter = new EventsAdapter(getActivity(), eventList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        return view;
    }

    public void updateArticleView(int position) {
        if (position == 0) {
            Iterator<Event> eventIterator =  eventList.iterator();
            while(eventIterator.hasNext()) {
                Event e = eventIterator.next();
                if (e.getCategoryId() != 19) {
                    eventIterator.remove();
                }
            }
            recyclerView.setAdapter(adapter);


            Log.d("EventListFragment", "cinema clicked");
        }
    }


}
