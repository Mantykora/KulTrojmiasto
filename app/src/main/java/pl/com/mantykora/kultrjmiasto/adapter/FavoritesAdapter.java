package pl.com.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.DetailActivity;
import pl.com.mantykora.kultrjmiasto.R;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private static List<FavoriteEntry> eventList;
    private static Context context;


    public FavoritesAdapter(Context context) {
        this.context = context;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView placeTextView;
        TextView dateTextView;

        FavoritesViewHolder.FavoritesViewHolderClick clickListener;

        public FavoritesViewHolder(View itemView, FavoritesViewHolderClick viewHolderClick) {
            super(itemView);
            clickListener = viewHolderClick;


            nameTextView = itemView.findViewById(R.id.fav_title_tv);
            placeTextView = itemView.findViewById(R.id.fav_place_tv);
            dateTextView = itemView.findViewById(R.id.fav_date_tv);

            nameTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
            clickListener.onFavoriteListItem(view);

            int position = (int) view.getTag();

            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);

            FavoriteEntry favoriteEntry = eventList.get(position);

            intent.putExtra("singleFavorite", (Parcelable) favoriteEntry);

            itemView.getContext().startActivity(intent);

    }

    public interface FavoritesViewHolderClick {
            void onFavoriteListItem(View view);
    }
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_favorite, parent, false);
        return new FavoritesViewHolder(view, new FavoritesViewHolder.FavoritesViewHolderClick() {

            public void onFavoriteListItem(View view) {
                Log.d("FavoritesAdapter", "click");
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        FavoriteEntry favoriteEntry = eventList.get(position);
        holder.nameTextView.setTag(position);

        DateTime dateTime = new DateTime(favoriteEntry.getDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        String dateString = dateTime.toString(dateTimeFormatter);

        String placeString = favoriteEntry.getPlace();


        if (placeString.length() > 35 ) {
            placeString = placeString.substring(0, 35) + "...";
        }

        holder.nameTextView.setText(favoriteEntry.getTitle());
        holder.placeTextView.setText(placeString);
        holder.dateTextView.setText(dateString);

    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();


    }

    public void setFavorites(List<FavoriteEntry> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public FavoriteEntry getFavorite(int position) {
        return eventList.get(position);
    }

    public List<FavoriteEntry> getFavorites() {
        return eventList;
    }
}
