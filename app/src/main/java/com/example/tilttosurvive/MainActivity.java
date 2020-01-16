package com.example.tilttosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Repo repo;
    private Button btnStart;
    private Button btnInstructions;
    private TextView tvHighScore;
    private MediaPlayer soundStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        getHighScores();
    }

    public void initialize() {
        tvHighScore = findViewById(R.id.tvHighScore);

        repo = new Repo(getApplication());

        soundStart = MediaPlayer.create(this, R.raw.new_game);

        final Intent i = new Intent(this, GameActivity.class);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundStart.start();
                startActivity(i);
            }
        });

        btnInstructions = findViewById(R.id.btnInstructions);
        btnInstructions.setOnClickListener(new InstructionsListener());
    }

    public void getHighScores(){
        repo.insert(new Score("Rasmus", 10));
        repo.insert(new Score("Fredrik", 15));
        ArrayList<Score> list = (ArrayList)repo.getHighscores();
        Log.d(TAG, "getHighScores: " + list.toString());
        String scores = "";
        for (int i = 0; i < list.size(); i ++){
            scores += list.get(i).getName() + " - " + list.get(i).getScore() + "\n";
            Log.d(TAG, "getHighScores: " + list.get(i).getName());
            Log.d(TAG, "getHighScores: " + list.get(i).getScore());
        }
        tvHighScore.setText(scores);
    }

//    public void setsAcc(String string){
//        tvAcc.setText(string);
//    }
//
//    public void setsGyroscope(String string){
//        tvGyro.setText(string);
//    }
//
//    public void setsProximity(String string){
//        tvProxy.setText(string);
//    }

//    public void registerSensors(){
//        if(isAccPresent){
//            sensorManager.registerListener(sensorListener, sAcc, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//        if(isGyroPresent){
//            sensorManager.registerListener(sensorListener, sGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//        if(isProximityPresent){
//            sensorManager.registerListener(sensorListener, sProximity, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//    }

//    public void unRegisterSensors(){
//        sensorManager.unregisterListener(sensorListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unRegisterSensors();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerSensors();
//    }

    private class InstructionsListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, InstuctionActivity.class);
            startActivity(intent);
        }
    }
}

