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
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;
import pl.com.mantykora.kultrjmiasto.model.Event;



public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private static List<FavoriteEntry> eventList;
    private static Context context;



    public FavoritesAdapter(Context context) {
        this.context = context;
        //this.eventList = dataList;
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView placeTextView;
        TextView dateTextView;

        public FavoritesViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.fav_title_tv);
            placeTextView = itemView.findViewById(R.id.fav_place_tv);
            dateTextView = itemView.findViewById(R.id.fav_date_tv);

        }
    }
    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_favorite, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        FavoriteEntry favoriteEntry = eventList.get(position);

        holder.nameTextView.setText(favoriteEntry.getTitle());
        holder.placeTextView.setText(favoriteEntry.getPlace());
        holder.dateTextView.setText(favoriteEntry.getDate());

    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();


    }    public void setFavorites(List<FavoriteEntry> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public List<FavoriteEntry> getFavorites() {
        return eventList;
    }
}
