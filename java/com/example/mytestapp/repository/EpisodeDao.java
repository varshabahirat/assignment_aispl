package com.example.mytestapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mytestapp.models.Episode;

import java.util.List;
@androidx.room.Dao
public interface EpisodeDao {
    @Insert
    void insert(Episode model);
    @Update
    void update(Episode model);
    @Delete
    void delete(Episode model);
    @Query("DELETE FROM episode_table")
    void deleteAllCourses();
    @Query("SELECT * FROM episode_table ORDER BY name ASC")
    LiveData<List<Episode>> getAllCourses();
}
