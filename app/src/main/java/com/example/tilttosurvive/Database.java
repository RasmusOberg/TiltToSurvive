package com.example.tilttosurvive;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {Score.class}, version = 3, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract com.example.tilttosurvive.Database.Dao dao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database").
                    fallbackToDestructiveMigration().addCallback(roomCallback).allowMainThreadQueries().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        public void onCreate(SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private PopulateDbAsyncTask(Database db){
            dao = db.dao();
        }

        protected Void doInBackground(Void... voids){
            return null;
        }
    }
}
