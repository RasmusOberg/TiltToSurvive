package com.example.tilttosurvive;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "highscore_table")
public class Score {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "score")
    private double score;

    @ColumnInfo(name = "user")
    private String name;

    public Score(String name, double score){
        this.name = name;
        this.score = score;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public double getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
