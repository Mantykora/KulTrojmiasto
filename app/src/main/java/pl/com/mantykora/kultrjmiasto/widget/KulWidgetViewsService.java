package pl.com.mantykora.kultrjmiasto.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import pl.com.mantykora.kultrjmiasto.AppExecutors;
import pl.com.mantykora.kultrjmiasto.MainViewModel;
import pl.com.mantykora.kultrjmiasto.R;
import pl.com.mantykora.kultrjmiasto.database.AppDatabase;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;

public class KulWidgetViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new KulRemotViewsFactory(this.getApplicationContext(), intent);
    }
}

class KulRemotViewsFactory implements RemoteViewsService.RemoteViewsFactory {
     private Context context;
     private Intent intent;
     //private ArrayList<FavoriteEntry> favorites;
    List<FavoriteEntry> favorites;
     private int appWidgetId;
    private AppDatabase mDb;


    public KulRemotViewsFactory(Context context , Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        mDb = AppDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                favorites = mDb.favoriteDao().loadFavWidget();

            }
        });
    }

    @Override
    public void onCreate() {
//        mDb = AppDatabase.getInstance(context);
//        favorites = mDb.favoriteDao().loadFavWidget();

    }

    //LiveData<FavoriteEntry> likedEntry =  mDb.favoriteDao().loadTaskById(event.getId());
//       likedEntry.observe(this, new Observer<FavoriteEntry>() {
//           @Override
//           public void onChanged(@Nullable FavoriteEntry favoriteEntry) {
//               if (favoriteEntry != null) {
//                   if (favoriteEntry.getIsLiked()) {
//                       likeButton.setLiked(true);
//                   }
//               }
//           }
//       });

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (favorites != null && favorites != null) {
            return favorites.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
       // favorites = mDb.favoriteDao().loadAllFavorites();
       // favorites = mDb.favoriteDao().loadFavWidget();

        FavoriteEntry favorite = favorites.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item_tv, favorite.getTitle());

        Bundle extras = new Bundle();
        extras.putInt(KulWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_item_tv, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}