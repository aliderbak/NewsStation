package com.example.newstation.database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
@Dao
public interface SportDao {
    @Query("SELECT * FROM sport")
   List<SportTable>getAll();
    @Insert
    void insertAllSportNews (SportTable ... mSportList);
    @Insert
    void insertOne(SportTable sportTable);
    @Delete
    void deleteAllSportNews(SportTable ... mSportList);
}
