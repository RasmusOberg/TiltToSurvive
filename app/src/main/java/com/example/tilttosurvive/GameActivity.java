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
    private MediaPlayer soundJumpForward, soundJumpBack, soundJumpRight, soundJumpLeft, soundDead, soundBgMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this, getApplication(), this);
        setContentView(gameView);
        sensorListener = new SensorListener(this);
        soundJumpForward = MediaPlayer.create(this, R.raw.jump);
        soundJumpBack = MediaPlayer.create(this, R.raw.jump);
        soundJumpLeft = MediaPlayer.create(this, R.raw.jump);
        soundJumpRight = MediaPlayer.create(this, R.raw.jump);

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

    public void registerAcc(){
        sensorManager.registerListener(sensorListener, sAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterAcc(){
        sensorManager.unregisterListener(sensorListener, sAcc);
    }

    public void registerGyro(){
        sensorManager.registerListener(sensorListener, sGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterGyro(){
        sensorManager.unregisterListener(sensorListener, sGyroscope);
    }


    public void showMonsters(boolean bool){
        gameView.showMonsters(bool);
    }

    public void playSoundJumpForward(){
        soundJumpForward.start();
    }
    public void playSoundJumpBack(){
        soundJumpBack.start();
    }
    public void playSoundJumpRight(){
        soundJumpRight.start();
    }
    public void playSoundJumpLeft(){
        soundJumpLeft.start();
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

    public void unRegisterSensors(){
        sensorManager.unregisterListener(sensorListener);
    }

    public void moveForward(){
        gameView.moveForward();
        playSoundJumpForward();
    }

    public void moveLeft(){
        gameView.moveLeft();
        playSoundJumpLeft();
    }

    public void moveRight(){
        gameView.moveRight();
        playSoundJumpRight();
    }

    public void moveDown(){
        gameView.moveDown();
        playSoundJumpBack();
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

    public void showTimer(){
        gameView.showTimer();
    }
}
