package com.op.smss.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.op.smss.model.Items;

import java.util.List;

@Dao
public interface smsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Items item);

    @Query("DELETE FROM sms_table")
    void deleteAll();


    @Query("DELETE FROM sms_table WHERE `key` == :items ")
    void deleteItem(String items);

    @Query("SELECT * FROM sms_table")
    List<Items> getItems();

    @Query("SELECT * FROM sms_table WHERE `key` == :k")
    List<Items> getItem(String k);


    @Query("SELECT * FROM sms_table")
    LiveData<List<Items>> getAllItem();

    @Update
    void  update(Items items);


}
