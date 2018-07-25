package pl.com.mantykora.kultrjmiasto;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;

import java.util.logging.LogRecord;

import io.reactivex.annotations.NonNull;

public class AppExecutors {

    public static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;


    public AppExecutors(Executor diskoIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskoIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());

            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor networkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }


    }
}
