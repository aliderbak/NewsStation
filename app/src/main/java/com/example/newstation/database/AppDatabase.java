package com.example.newstation.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities =  {SportTable.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;
    private static final String DATABASE_NAME = "news_database";

    public abstract SportDao sportDao();

    public synchronized static AppDatabase getDatabaseInstance(Context context){
        if(mInstance == null){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
    public static void destroyInstance() {

        mInstance.clearAllTables();


        Log.v("Cleared","DONE");
    }
}
