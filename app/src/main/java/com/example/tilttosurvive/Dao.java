package com.example.tilttosurvive;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(Score score);

    @Update
    void update(Score score);

    @Query("SELECT * FROM highscore_table ORDER BY score ASC LIMIT 3")
    List<Score> getAllHighscores();
}
