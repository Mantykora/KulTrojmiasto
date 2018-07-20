package pl.com.mantykora.kultrjmiasto.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.com.mantykora.kultrjmiasto.model.Event;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    List<FavoriteEntry> loadAllFavorites();

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Delete
    void deleteTask(FavoriteEntry favoriteEntry);

}
