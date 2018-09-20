package eu.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

import eu.mantykora.kultrjmiasto.DetailActivity;
import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.model.Attachment;
import eu.mantykora.kultrjmiasto.model.CategoryEnum;
import eu.mantykora.kultrjmiasto.model.Event;
import eu.mantykora.kultrjmiasto.utils.ImageTransformation;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private static List<eu.mantykora.kultrjmiasto.model.Event> eventList;
    private Context context;

    public EventsAdapter(List<eu.mantykora.kultrjmiasto.model.Event> dataList, Context context) {
        eventList = dataList;
        this.context = context;
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final View view;

        final TextView nameTv;
        final TextView hourTextView;
        final ImageView imageIv;
        final TextView dateTv;
        final ImageView eventCategoryIv;
        final CardView cardView;

        final EventsViewHolder.ViewHolderClick clickListener;

        EventsViewHolder(View itemView, ViewHolderClick viewHolderClick) {
            super(itemView);
            view = itemView;
            clickListener = viewHolderClick;

            nameTv = view.findViewById(R.id.event_title_tv);
            hourTextView = view.findViewById(R.id.event_hour_tv);
            imageIv = view.findViewById(R.id.event_iv);
            dateTv = view.findViewById(R.id.event_date_tv);
            eventCategoryIv = view.findViewById(R.id.event_category_iv);
            cardView = view.findViewById(R.id.list_item_cv);


            imageIv.setOnClickListener(this);

        }

        /*
          OnClick in RecyclerView from: https://stackoverflow.com/a/24933117/8131467



         */
        @Override
        public void onClick(View view) {
            clickListener.onEventListItem(view);
            int position = (int) view.getTag();

            Intent intent = new Intent(itemView.getContext(), eu.mantykora.kultrjmiasto.DetailActivity.class);


            eu.mantykora.kultrjmiasto.model.Event singleEvent = eventList.get(position);

            intent.putExtra("singleEvent", singleEvent);

            itemView.getContext().startActivity(intent);

        }

        interface ViewHolderClick {
            void onEventListItem(View view);
        }
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_event, parent, false);
        return new EventsViewHolder(view, view1 -> Log.d("EventsAdapter", "onclick"));
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

        eu.mantykora.kultrjmiasto.model.Event event = eventList.get(position);
        holder.imageIv.setTag(position);
        String imageLinkString;

        int categoryId = event.getCategoryId();

        List<eu.mantykora.kultrjmiasto.model.Attachment> attachments = event.getAttachments();


        eu.mantykora.kultrjmiasto.model.CategoryEnum enumValue = eu.mantykora.kultrjmiasto.model.CategoryEnum.forCode(categoryId);


        if (enumValue == null) {
            Log.d("EventAdapter", "enmu value is null");
            return;
        }


        DateTime dateTime = new DateTime(eventList.get(position).getStartDate());
        org.joda.time.format.DateTimeFormatter hourFormatter = DateTimeFormat.forPattern("HH:mm");
        String eventHour = dateTime.toString(hourFormatter);

        org.joda.time.format.DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("dd.MM");
        String eventDate = dateTime.toString(dateFormatter);

        if (dateTime.toLocalDate().equals(new LocalDate())) {

            holder.dateTv.setText(R.string.dzisiaj);
        } else if (dateTime.toLocalDate().equals(new LocalDate().plusDays(1))) {

            holder.dateTv.setText(R.string.jutro);
        } else {
            holder.dateTv.setText(eventDate);
        }

        String eventName = eventList.get(position).getName();
        if (eventName.length() > 45) {
            eventName = eventName.substring(0, 45) + "...";
        }

        if (eventName.contains("<p>") || eventName.contains("&nbsp;") || eventName.contains("b") || eventName.contains("[embed]") || eventName.contains("&quot;")) {
            holder.nameTv.setText(Html.fromHtml(eventName));

        } else {
            holder.nameTv.setText(eventName);
        }



        int drawable = enumValue.getDrawable();
        holder.eventCategoryIv.setImageResource(drawable);
        holder.hourTextView.setText(eventHour);

        if (attachments.size() > 0) {

            if (attachments.size() > 1) {
                imageLinkString = attachments.get(1).getFileName();
            } else {
                imageLinkString = attachments.get(0).getFileName();
            }
            Transformation transformation = new ImageTransformation(context);
            Picasso.get().load(imageLinkString)
                    .transform(transformation)
                    .error(R.color.transparent)
                    .placeholder(R.drawable.progress_animation)
                    .into(holder.imageIv);
        } else {
            holder.imageIv.setImageDrawable(null);
        }


    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
