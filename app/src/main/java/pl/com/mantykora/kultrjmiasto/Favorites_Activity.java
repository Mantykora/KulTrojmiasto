package pl.com.mantykora.kultrjmiasto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mantykora.kultrjmiasto.adapter.FavoritesAdapter;
import pl.com.mantykora.kultrjmiasto.database.AppDatabase;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;

public class Favorites_Activity extends AppCompatActivity {
    AppDatabase mDb;

    @BindView(R.id.favorites_rv)
    RecyclerView favoritesRv;

    private FavoritesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
       mDb = AppDatabase.getInstance(getApplicationContext());


        adapter = new FavoritesAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRv.setLayoutManager(layoutManager);

       // LiveData<List<FavoriteEntry>> favorites = mDb.favoriteDao().loadAllFavorites();

        setupViewModel();

        //adapter.setFavorites(mDb.favoriteDao().loadAllFavorites());
        favoritesRv.setAdapter(adapter);


    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                Log.d("FavoritesActivity", "Receiving database update from LiveData in ViewModel");
                adapter.setFavorites(favoriteEntries);
            }
        });
    }
}
