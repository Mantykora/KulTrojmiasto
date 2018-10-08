package eu.mantykora.kultrjmiasto.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import eu.mantykora.kultrjmiasto.database.FavoriteEntry;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<eu.mantykora.kultrjmiasto.database.FavoriteEntry>> loadAllFavorites();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry);

    @Delete
    void deleteTask(eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry);


    @Query("DELETE FROM favorite")
    void deleteAll();

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<eu.mantykora.kultrjmiasto.database.FavoriteEntry> loadTaskById(int id);

    @Query("SELECT * FROM favorite WHERE id = :id")
    eu.mantykora.kultrjmiasto.database.FavoriteEntry loadTaskByIdWithoutLiveData(int id);


}
