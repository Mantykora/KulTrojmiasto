package eu.mantykora.kultrjmiasto;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import eu.mantykora.kultrjmiasto.database.AppDatabase;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;

class MainViewModel extends AndroidViewModel {
    private final LiveData<List<eu.mantykora.kultrjmiasto.database.FavoriteEntry>> favorites;

    public MainViewModel(@NonNull Application application) {
        super(application);
        eu.mantykora.kultrjmiasto.database.AppDatabase database = eu.mantykora.kultrjmiasto.database.AppDatabase.getInstance(this.getApplication());
        favorites = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<eu.mantykora.kultrjmiasto.database.FavoriteEntry>> getFavorites() {
        return favorites;
    }
}
