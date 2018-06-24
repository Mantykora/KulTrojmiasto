package pl.com.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.R;
import pl.com.mantykora.kultrjmiasto.model.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder>{

    private List<Event> dataList;
    private Context context;

   public EventsAdapter(Context context, List<Event> dataList) {
       this.context = context;
       this.dataList = dataList;
   }

   class EventsViewHolder extends RecyclerView.ViewHolder {

       public final View view;

       TextView nameTv;

       EventsViewHolder(View itemView) {
           super(itemView);
           view = itemView;

           nameTv = view.findViewById(R.id.event_title_tv);

       }
   }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_event, parent, false);
        return new EventsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

       holder.nameTv.setText(dataList.get(position).getName());




    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
