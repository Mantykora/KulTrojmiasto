package eu.mantykora.kultrjmiasto;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mantykora.kultrjmiasto.AppExecutors;
import eu.mantykora.kultrjmiasto.MainViewModel;
import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter;
import eu.mantykora.kultrjmiasto.database.AppDatabase;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;

public class FavoritesActivity extends AppCompatActivity {
    private eu.mantykora.kultrjmiasto.database.AppDatabase mDb;

    @BindView(R.id.favorites_rv)
    RecyclerView favoritesRv;

    @BindView(R.id.empty_view_favs)
    ConstraintLayout constraintLayout;

    private eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);
        mDb = eu.mantykora.kultrjmiasto.database.AppDatabase.getInstance(getApplicationContext());


        adapter = new eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRv.setLayoutManager(layoutManager);
        favoritesRv.addItemDecoration(new DividerItemDecoration(favoritesRv.getContext(), DividerItemDecoration.VERTICAL));

        favoritesRv.setVisibility(View.GONE);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foreground = ((eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;

                    getDefaultUIUtil().onSelected(foreground);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foreground = ((eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;
                getDefaultUIUtil().clearView(foreground);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foreground = ((eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry = adapter.getFavorite(position);
                removeFavoriteFromDatabase(favoriteEntry);


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(favoritesRv);
        constraintLayout.setVisibility(View.VISIBLE);


        setupViewModel();

        favoritesRv.setAdapter(adapter);


    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, favoriteEntries -> {
            if (favoriteEntries.size() > 0) {
                favoritesRv.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);
            }
            adapter.setFavorites(favoriteEntries);
        });
    }

    private void removeFavoriteFromDatabase(eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry) {
        eu.mantykora.kultrjmiasto.AppExecutors.getInstance().diskIO().execute(() -> mDb.favoriteDao().deleteTask(favoriteEntry));
    }
}
