package com.siat.diayan.journalapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

@Database(entities = {TaskEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class JournalDatabase extends RoomDatabase {

    private static final String LOG_TAG = JournalDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "journaldaily";
    private static JournalDatabase sInstance;

    public static JournalDatabase getInstance(Context context){

        if (sInstance == null){

            synchronized (LOCK){
                Log.d(TAG, "creating a new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        JournalDatabase.class, JournalDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "getting the database instance");
        return sInstance;
    }

    public abstract TaskDao taskDao();
}
