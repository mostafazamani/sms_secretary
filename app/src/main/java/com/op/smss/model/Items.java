package com.op.smss.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_table")
public class Items {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "value")
    private String value;

    public Items(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
