package com.example.tilttosurvive.Database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repo {
    private Dao dao;
    private List<Score> highscores;


    public Repo(Application application){
        Database db = Database.getInstance(application);
        dao = db.dao();
        try{
            highscores = dao.getAllHighscores();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Score score){
        new InsertScoreAsync(dao).execute(score);
    }

    public void update(Score score){
        new UpdateScoreAsync(dao).execute(score);
    }

    public List<Score> getHighscores(){
        return highscores;
    }

    private class InsertScoreAsync extends AsyncTask<Score, Void, Void>{
        private Dao dao;

        private InsertScoreAsync(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            dao.insert(scores[0]);
            return null;
        }
    }

    private class UpdateScoreAsync extends AsyncTask<Score, Void, Void>{
        private Dao dao;

        private UpdateScoreAsync(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            dao.update(scores[0]);
            return null;
        }
    }
}
