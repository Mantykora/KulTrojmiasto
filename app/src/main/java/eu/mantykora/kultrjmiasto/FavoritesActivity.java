package eu.mantykora.kultrjmiasto;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mantykora.kultrjmiasto.adapter.FavoritesAdapter;
import eu.mantykora.kultrjmiasto.database.AppDatabase;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;


public class FavoritesActivity extends AppCompatActivity {
    private AppDatabase mDb;

    @BindView(R.id.favorites_rv)
    RecyclerView favoritesRv;

    @BindView(R.id.empty_view_favs)
    ConstraintLayout constraintLayout;

    private FavoritesAdapter adapter;

    @BindView(R.id.delete_all_button)
    ImageButton deleteAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);
        mDb = AppDatabase.getInstance(getApplicationContext());


        adapter = new FavoritesAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        favoritesRv.setLayoutManager(layoutManager);
        favoritesRv.addItemDecoration(new DividerItemDecoration(favoritesRv.getContext(), DividerItemDecoration.VERTICAL));

        favoritesRv.setVisibility(View.GONE);

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = AskIfDelete();
                dialog.show();

            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foreground = ((FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;

                    getDefaultUIUtil().onSelected(foreground);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foreground = ((FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;
                getDefaultUIUtil().clearView(foreground);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foreground = ((FavoritesAdapter.FavoritesViewHolder) viewHolder).foregroundLayout;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                FavoriteEntry favoriteEntry = adapter.getFavorite(position);
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

    private void removeFavoriteFromDatabase(FavoriteEntry favoriteEntry) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.favoriteDao().deleteTask(favoriteEntry));
    }

    private void deleteAllFavs() {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.favoriteDao().deleteAll());
    }

    private AlertDialog AskIfDelete()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Usun wszystkie")
                .setMessage("Czy na pewno chcesz usunąć wszystkie ulubione?")
                .setIcon(R.drawable.ic_delete_black_36dp)

                .setPositiveButton("UsuN", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(FavoritesActivity.this, "usuwanie", Toast.LENGTH_SHORT).show();
                        deleteAllFavs();

                        dialog.dismiss();
                    }

                })



                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return alertDialog;

    }
}
