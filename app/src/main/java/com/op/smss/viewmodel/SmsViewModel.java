package com.op.smss.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.op.smss.database.SmsRepository;
import com.op.smss.model.Items;

import java.util.List;

public class SmsViewModel extends AndroidViewModel {

    private SmsRepository mRepository;

    private final LiveData<List<Items>> mAllItem;

    public SmsViewModel (Application application) {
        super(application);
        mRepository = new SmsRepository(application);
        mAllItem = mRepository.getAllItems();
    }

    public LiveData<List<Items>> getAllWords() { return mAllItem; }

    public void insert(Items item) { mRepository.insert(item); }
}
