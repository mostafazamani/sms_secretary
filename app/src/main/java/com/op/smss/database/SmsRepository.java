package com.op.smss.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.op.smss.background.ItemDataBase;
import com.op.smss.model.Items;

import java.util.List;

public class SmsRepository {

    private smsDao msmsDao;
    private LiveData<List<Items>> mAllsms;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public SmsRepository(Application application) {
        ItemDataBase db = ItemDataBase.getDatabase(application);
        msmsDao = db.smswordDao();
        mAllsms = msmsDao.getAllItem();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Items>> getAllItems() {
        return mAllsms;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Items item) {
        ItemDataBase.databaseWriteExecutor.execute(() -> {
            msmsDao.insert(item);
        });
    }
}
