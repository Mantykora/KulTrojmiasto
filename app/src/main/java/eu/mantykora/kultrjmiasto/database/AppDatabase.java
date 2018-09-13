package eu.mantykora.kultrjmiasto.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import eu.mantykora.kultrjmiasto.database.FavoriteDao;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;

@Database(entities = {eu.mantykora.kultrjmiasto.database.FavoriteEntry.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "kulDatabase";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();

            }

        }
        return sInstance;
    }

    public abstract eu.mantykora.kultrjmiasto.database.FavoriteDao favoriteDao();


}
