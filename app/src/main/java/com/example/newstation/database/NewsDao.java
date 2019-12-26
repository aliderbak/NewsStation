package com.example.newstation.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<NewsTable> getAll();


    @Insert
    void insertAllNews (NewsTable ... mNewsTables);
    @Insert
    void insertOne(NewsTable newsTable);
    @Delete
    void deleteAllNews(NewsTable ... mNewsTables);
}
