package com.example.tilttosurvive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tilttosurvive.Database.Repo;
import com.example.tilttosurvive.Database.Score;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sAcc, sGyroscope, sProximity;
    SensorListener sensorListener;
    boolean isAccPresent, isGyroPresent, isProximityPresent;
    float gyroX, gyroY, gyroZ, accX, accY, accZ;
    private static final String TAG = "MainActivity";
    TextView tvAcc, tvGyro, tvProxy;
    private Button btnStart;
    private Button btnInstructions;
    private ImageView image;
    private Button button;
    private Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new Repo(getApplication());
        repo.insert(new Score("Rasmus", 0.5));
        List<Score> scores = repo.getHighscores();
        Log.d(TAG, "onCreate: " + scores.get(0).getScore());
        Log.d(TAG, "onCreate: " + scores.size());
        initialize();
//        sensorListener = new SensorListener(this);
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorListener, sAcc, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, sAcc.getName() + " is registered", Toast.LENGTH_SHORT).show();
            isAccPresent = true;
        } else {

            isAccPresent = false;
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            sGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(sensorListener, sGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, sGyroscope.getName() + " is registered", Toast.LENGTH_SHORT).show();
            isGyroPresent = true;
        } else {

            isGyroPresent = false;
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            sProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            sensorManager.registerListener(sensorListener, sProximity, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, sProximity.getName() + " is registered", Toast.LENGTH_SHORT).show();
            isProximityPresent = true;
        } else {

            isProximityPresent = false;
        }

    }

    public void initialize() {
        tvAcc = findViewById(R.id.tvAcc);
        tvGyro = findViewById(R.id.tvGyro);
        tvProxy = findViewById(R.id.tvProxy);

        final Intent i = new Intent(this, GameActivity.class);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ObjectAnimator animation = ObjectAnimator.ofFloat(btnTest, "translationX", 100f);
//                animation.setDuration(2000);
//                animation.start();
//                btnTest.animate().translationX(100f).setDuration(1000);
                startActivity(i);
            }
        });

        btnInstructions = findViewById(R.id.btnInstructions);
        btnInstructions.setOnClickListener(new InstructionsListener());
    }

    public void setsAcc(String string){
        tvAcc.setText(string);
    }

    public void setsGyroscope(String string){
        tvGyro.setText(string);
    }

    public void setsProximity(String string){
        tvProxy.setText(string);
    }


    public void registerSensors(){
        if(isAccPresent){
            sensorManager.registerListener(sensorListener, sAcc, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isGyroPresent){
            sensorManager.registerListener(sensorListener, sGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isProximityPresent){
            sensorManager.registerListener(sensorListener, sProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unRegisterSensors(){
        sensorManager.unregisterListener(sensorListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterSensors();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensors();
    }

    private class InstructionsListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, InstuctionActivity.class);
            startActivity(intent);
        }
    }

    public void updateScoreDB(Score score){
        repo.insert(score);
    }
}

