package pl.com.mantykora.kultrjmiasto.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import pl.com.mantykora.kultrjmiasto.DetailActivity;
import pl.com.mantykora.kultrjmiasto.R;
import pl.com.mantykora.kultrjmiasto.model.Attachment;
import pl.com.mantykora.kultrjmiasto.model.CategoryEnum;
import pl.com.mantykora.kultrjmiasto.model.Event;
import pl.com.mantykora.kultrjmiasto.utils.ImageTransformation;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private static List<Event> eventList;
    private Context context;

    public EventsAdapter(Context context, List<Event> dataList) {
        this.context = context;
        this.eventList = dataList;
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final View view;

        TextView nameTv;
        TextView categoryTv;
        TextView hourTextView;
        ImageView imageIv;
        TextView dateTv;
        ImageView eventCategoryIv;
        CardView cardView;

        EventsViewHolder.ViewHolderClick clickListener;

        public EventsViewHolder(View itemView, ViewHolderClick viewHolderClick) {
            super(itemView);
            view = itemView;
            clickListener = viewHolderClick;

            nameTv = view.findViewById(R.id.event_title_tv);
//            categoryTv = view.findViewById(R.id.event_category_tv);
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

            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);


           // Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(itemView.getContext(), )

//            Intent intent = new Intent(this, DetailsActivity.class);
//// Pass data object in the bundle and populate details activity.
//            intent.putExtra(DetailsActivity.EXTRA_CONTACT, contact);
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation(this, (View)ivProfile, "profile");
//            startActivity(intent, options.toBundle());

            Event singleEvent = eventList.get(position);

            Log.d("EventsAdapter", "" + singleEvent.getName());

            intent.putExtra("singleEvent", singleEvent);

           itemView.getContext().startActivity(intent);
            //TODO check android version
            //TODO first it shows background and than image - that's wrong
//            ActivityOptions options = ActivityOptions.
//                    makeSceneTransitionAnimation((Activity) itemView.getContext(),
//                            cardView,
//                            ViewCompat.getTransitionName(cardView));
//            itemView.getContext().startActivity(intent, options.toBundle());
//            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity)itemView.getContext()).toBundle();
//            itemView.getContext().startActivity(intent, bundle);

            Log.d("EventsAdapter", "" + position);
        }

        public interface ViewHolderClick {
            void onEventListItem(View view);
        }
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_event, parent, false);
        return new EventsViewHolder(view, new EventsViewHolder.ViewHolderClick() {
            public void onEventListItem(View view) {

                Log.d("EventsAdapter", "onclick");

            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {

        //TODO lighter onBind
        Event event = eventList.get(position);
        holder.imageIv.setTag(position);
        String imageLinkString;

        int categoryId = event.getCategoryId();

        List<Attachment> attachments = event.getAttachments();


        CategoryEnum enumValue = CategoryEnum.forCode(categoryId);


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

            holder.dateTv.setText("dzisiaj");
        }
        else if(dateTime.toLocalDate().equals(new LocalDate().plusDays(1))) {

            holder.dateTv.setText("jutro");
        }
        else {
            holder.dateTv.setText(eventDate);
        }

        String eventName = eventList.get(position).getName();
        if(eventName.length() > 45) {
            eventName = eventName.substring(0, 45) + "...";
        }

        holder.nameTv.setText(eventName);
//        holder.categoryTv.setText(enumValue.getName());


        int drawable =  enumValue.getDrawable();
        holder.eventCategoryIv.setImageResource(drawable);
        holder.hourTextView.setText(eventHour);

        if (attachments.size() > 0 ) {

            if (attachments.size() > 1) {
                imageLinkString = attachments.get(1).getFileName();
            } else {
                imageLinkString = attachments.get(0).getFileName();
            }
            Transformation transformation = new ImageTransformation();
            Picasso.get().load(imageLinkString)
                    .transform(transformation)
                    .error(R.drawable.baseline_credit_card_24)
                    .placeholder( R.drawable.progress_animation )
                    .into(holder.imageIv);  }
                    else  {
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
