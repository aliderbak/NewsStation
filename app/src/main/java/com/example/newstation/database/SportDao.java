package com.example.newstation.database;
import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SportDao {
    @Query("SELECT * FROM sport")
     List<SportTable> getAll();


    @Insert
    void insertAllSportNews (SportTable ... mSportList);
    @Insert
    void insertOne(SportTable sportTable);
    @Delete
    void deleteAllSportNews(SportTable ... mSportList);
}
