package pl.com.mantykora.kultrjmiasto.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<FavoriteEntry>> loadAllFavorites();

    @Query("SELECT * FROM favorite")
    List<FavoriteEntry> loadFavWidget();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteTask(FavoriteEntry favoriteEntry);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<FavoriteEntry> loadTaskById(int id);

    @Query("SELECT * FROM favorite WHERE id = :id")
    FavoriteEntry loadTaskByIdWithoutLiveData(int id);


}
