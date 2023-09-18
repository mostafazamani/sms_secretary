package com.op.smss.background;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.op.smss.database.smsDao;
import com.op.smss.model.Items;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Items.class}, version = 1, exportSchema = false)
public abstract class ItemDataBase extends RoomDatabase {

    public abstract smsDao smswordDao();

    private static volatile ItemDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ItemDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ItemDataBase.class, "sms_database"  ).allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
