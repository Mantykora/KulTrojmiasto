package pl.com.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.R;
import pl.com.mantykora.kultrjmiasto.model.Attachment;
import pl.com.mantykora.kultrjmiasto.model.CategoryEnum;
import pl.com.mantykora.kultrjmiasto.model.Event;
import pl.com.mantykora.kultrjmiasto.utils.ImageTransformation;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Event> eventList;
    //private List<Attachment> attachmentList;
    private Context context;

    public EventsAdapter(Context context, List<Event> dataList) {
        this.context = context;
        this.eventList = dataList;
       // this.attachmentList = attachmentList;
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {

        public final View view;

        TextView nameTv;
        TextView categoryTv;
        TextView hourTextView;
        ImageView imageIv;

        EventsViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            nameTv = view.findViewById(R.id.event_title_tv);
            categoryTv = view.findViewById(R.id.event_category_tv);
            hourTextView = view.findViewById(R.id.event_hour_tv);
            imageIv = view.findViewById(R.id.event_iv);

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
        Event event = eventList.get(position);
        String imageLinkString;

        int categoryId = event.getCategoryId();

        List<Attachment> attachments = event.getAttachments();



        CategoryEnum enumValue = CategoryEnum.forCode(categoryId);





        if (enumValue == null) {
            // TODO: log error
            return;
        }
        holder.nameTv.setText(eventList.get(position).getName());
        holder.categoryTv.setText(enumValue.getName());
        holder.hourTextView.setText(eventList.get(position).getStartDate());
        if (attachments.size() > 0) {

            if (attachments.size() > 1) {
                imageLinkString = attachments.get(1).getFileName();
            }
           else {
                imageLinkString = attachments.get(0).getFileName();
            }
            Transformation transformation = new ImageTransformation();
            Picasso.get().load(imageLinkString)
                    .transform(transformation)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageIv);
    }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
