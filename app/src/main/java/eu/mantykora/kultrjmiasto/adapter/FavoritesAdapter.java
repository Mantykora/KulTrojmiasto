package eu.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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

import eu.mantykora.kultrjmiasto.DetailActivity;
import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private static List<eu.mantykora.kultrjmiasto.database.FavoriteEntry> eventList;
    private Context context;


    public FavoritesAdapter(Context context) {
        this.context = context;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView nameTextView;
        final TextView placeTextView;
        final TextView dateTextView;
        final ConstraintLayout backgroundLayout;
        public final ConstraintLayout foregroundLayout;

        final FavoritesViewHolder.FavoritesViewHolderClick clickListener;

        FavoritesViewHolder(View itemView, FavoritesViewHolderClick viewHolderClick) {
            super(itemView);
            clickListener = viewHolderClick;


            nameTextView = itemView.findViewById(R.id.fav_title_tv);
            placeTextView = itemView.findViewById(R.id.fav_place_tv);
            dateTextView = itemView.findViewById(R.id.fav_date_tv);
            backgroundLayout = itemView.findViewById(R.id.background);
            foregroundLayout = itemView.findViewById(R.id.foreground);

            nameTextView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            clickListener.onFavoriteListItem(view);

            int position = (int) view.getTag();

            Intent intent = new Intent(itemView.getContext(), eu.mantykora.kultrjmiasto.DetailActivity.class);

            eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry = eventList.get(position);

            intent.putExtra("singleFavorite", favoriteEntry);

            itemView.getContext().startActivity(intent);

        }

        interface FavoritesViewHolderClick {
            void onFavoriteListItem(View view);
        }
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_favorite, parent, false);
        return new FavoritesViewHolder(view, view1 -> Log.d("FavoritesAdapter", "click"));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry = eventList.get(position);
        holder.nameTextView.setTag(position);

        DateTime dateTime = new DateTime(favoriteEntry.getDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        String dateString = dateTime.toString(dateTimeFormatter);

        String placeString = favoriteEntry.getPlace();


        if (placeString.length() > 35) {
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

    public void setFavorites(List<eu.mantykora.kultrjmiasto.database.FavoriteEntry> eventList) {
        FavoritesAdapter.eventList = eventList;
        notifyDataSetChanged();
    }

    public eu.mantykora.kultrjmiasto.database.FavoriteEntry getFavorite(int position) {
        return eventList.get(position);
    }

    public List<eu.mantykora.kultrjmiasto.database.FavoriteEntry> getFavorites() {
        return eventList;
    }
}
