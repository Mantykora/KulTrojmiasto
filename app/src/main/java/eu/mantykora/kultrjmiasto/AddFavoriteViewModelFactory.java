package eu.mantykora.kultrjmiasto;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import eu.mantykora.kultrjmiasto.AddFavoriteViewModel;
import eu.mantykora.kultrjmiasto.database.AppDatabase;

class AddFavoriteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final eu.mantykora.kultrjmiasto.database.AppDatabase mDb;
    private final int mFavoriteId;

    public AddFavoriteViewModelFactory(eu.mantykora.kultrjmiasto.database.AppDatabase database, int favoriteId) {
        mDb = database;
        mFavoriteId = favoriteId;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new eu.mantykora.kultrjmiasto.AddFavoriteViewModel(mDb, mFavoriteId);
    }

}
