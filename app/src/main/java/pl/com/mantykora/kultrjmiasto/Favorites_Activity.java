package pl.com.mantykora.kultrjmiasto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mantykora.kultrjmiasto.adapter.FavoritesAdapter;
import pl.com.mantykora.kultrjmiasto.database.AppDatabase;

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


        adapter.setFavorites(mDb.favoriteDao().loadAllFavorites());
        favoritesRv.setAdapter(adapter);


    }
}
