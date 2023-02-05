package com.example.mytestapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.example.mytestapp.localdb.ShowsDatabase;
import com.example.mytestapp.models.Episode;

import java.util.List;

public class LocalDBRepository {
    private EpisodeDao dao;
    private LiveData<List<Episode>> allCourses;
    public LocalDBRepository(Application application) {
        ShowsDatabase database = ShowsDatabase.getInstance(application);
        dao = database.EpisodeDao();
        allCourses = dao.getAllCourses();
    }
    public void insert(Episode model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }
    public void update(Episode model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }
    public LiveData<List<Episode>> getAllEpisodes() {
        return allCourses;
    }
    private static class InsertCourseAsyncTask extends AsyncTask<Episode, Void, Void> {
        private EpisodeDao dao;

        private InsertCourseAsyncTask(EpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Episode... model) {
            dao.insert(model[0]);
            return null;
        }
    }
    private static class UpdateCourseAsyncTask extends AsyncTask<Episode, Void, Void> {
        private EpisodeDao dao;

        private UpdateCourseAsyncTask(EpisodeDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Episode... models) {
            dao.update(models[0]);
            return null;
        }
    }
}
