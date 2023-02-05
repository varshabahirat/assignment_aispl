package com.example.mytestapp.localdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mytestapp.models.Episode;
import com.example.mytestapp.repository.EpisodeDao;

// Database Communication Layer
@Database(entities = {Episode.class}, version = 1)
public abstract class ShowsDatabase extends RoomDatabase {

    private static ShowsDatabase instance;

    public abstract EpisodeDao EpisodeDao();

    public static synchronized ShowsDatabase getInstance(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                                    ShowsDatabase.class, "shows_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(ShowsDatabase instance) {
            EpisodeDao dao = instance.EpisodeDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
