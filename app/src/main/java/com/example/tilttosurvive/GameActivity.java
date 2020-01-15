package com.example.tilttosurvive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class GameActivity extends Activity {
    private SensorManager sensorManager;
    private Sensor sAcc, sGyroscope, sProximity;
    private SensorListener sensorListener;
    private boolean isAccPresent, isGyroPresent, isProximityPresent;
    private GameView gameView;
    private MediaPlayer soundJump, soundDead, soundBgMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);
        setContentView(gameView);
        sensorListener = new SensorListener(this);
        soundJump = MediaPlayer.create(this, R.raw.jump);
        soundDead = MediaPlayer.create(this, R.raw.victory);
        soundBgMusic = MediaPlayer.create(this, R.raw.music);
        soundBgMusic.start();
        soundBgMusic.setLooping(true);
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

    public void playSoundJump(){
        soundJump.start();
    }

    public void playSoundDead(){
        soundDead.start();
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

    public void showTimer(boolean bool){
        gameView.showTimer(bool);
    }

    public void unRegisterSensors(){
        sensorManager.unregisterListener(sensorListener);
    }

    public void moveForward(){
        gameView.moveForward();
        playSoundJump();
    }

    public void moveLeft(){
        gameView.moveLeft();
        playSoundJump();
    }

    public void moveRight(){
        gameView.moveRight();
        playSoundJump();
    }

    public void moveDown(){
        gameView.moveDown();
        playSoundJump();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterSensors();
        soundBgMusic.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensors();
        soundBgMusic.start();
    }

    public void showTimer(Boolean b){
        gameView.showTimer(b);
    }
}
