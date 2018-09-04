package pl.com.mantykora.kultrjmiasto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mantykora.kultrjmiasto.adapter.FavoritesAdapter;
import pl.com.mantykora.kultrjmiasto.database.AppDatabase;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;

public class FavoritesActivity extends AppCompatActivity {
    AppDatabase mDb;

    @BindView(R.id.favorites_rv)
    RecyclerView favoritesRv;

    @BindView(R.id.empty_view_favs)
    ConstraintLayout constraintLayout;

    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);
        mDb = AppDatabase.getInstance(getApplicationContext());


        adapter = new FavoritesAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRv.setLayoutManager(layoutManager);
        favoritesRv.addItemDecoration(new DividerItemDecoration(favoritesRv.getContext(), DividerItemDecoration.VERTICAL));

        favoritesRv.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);


        setupViewModel();

        favoritesRv.setAdapter(adapter);


    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                Log.d("FavoritesActivity", "Receiving database update from LiveData in ViewModel");
                if ( favoriteEntries.size() > 0 ) {
                favoritesRv.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);}
                adapter.setFavorites(favoriteEntries);
            }
        });
    }
}
