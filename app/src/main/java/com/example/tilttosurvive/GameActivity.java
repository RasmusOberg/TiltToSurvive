package com.example.tilttosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GameActivity extends Activity {

    SensorManager sensorManager;
    Sensor sAcc, sGyroscope, sProximity;
    SensorListener sensorListener;
    boolean isAccPresent, isGyroPresent, isProximityPresent;
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(new GameView(this));
        sensorListener = new SensorListener(this);

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

}
