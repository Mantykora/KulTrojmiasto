package eu.mantykora.kultrjmiasto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import eu.mantykora.kultrjmiasto.database.AppDatabase;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;

class AddFavoriteViewModel extends ViewModel {

    private final LiveData<FavoriteEntry> favorite;

    public AddFavoriteViewModel(AppDatabase database, int taskId) {
        favorite = database.favoriteDao().loadTaskById(taskId);
    }

    public LiveData<FavoriteEntry> getFavorites() {
        return favorite;
    }
}
