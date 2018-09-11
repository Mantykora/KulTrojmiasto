package pl.com.mantykora.kultrjmiasto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import pl.com.mantykora.kultrjmiasto.database.AppDatabase;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;

class AddFavoriteViewModel extends ViewModel {

    private final LiveData<FavoriteEntry> favorite;

    public AddFavoriteViewModel(AppDatabase database, int taskId) {
        favorite = database.favoriteDao().loadTaskById(taskId);
    }

    public LiveData<FavoriteEntry> getFavorites() {
        return favorite;
    }
}
