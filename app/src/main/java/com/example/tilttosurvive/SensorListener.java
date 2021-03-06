package com.example.tilttosurvive;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.widget.Toast;

public class SensorListener implements SensorEventListener {

    GameActivity gameActivity;

    String acc = "", gyro = "", proxy = "";
    private float lastForwardX, lastBackwardX, lastForwardY, lastBackwardY;

    boolean forwardX = false, backwardX = false, forwardY = false, backwardY = false;
    boolean isFullStepTakenX = false, isFullStepTakenY = false;
    private float number;
    private int shakeThreshold = 10;


    public SensorListener(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // SHAKER
        float x, y, z, lastX = 0, lastY = 0, lastZ = 0;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            float deltaX = Math.abs(lastX - x);
            float deltaY = Math.abs(lastY - y);
            float deltaZ = Math.abs(lastZ - z);

            if ((deltaX > shakeThreshold && deltaY > shakeThreshold)
                    || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                    || (deltaY > shakeThreshold && deltaZ > shakeThreshold)) {
                Toast.makeText(gameActivity, "TOAST", Toast.LENGTH_SHORT).show();
                gameActivity.showMonsters(true);
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
//            x är fram och tillbaka
//            y är höger och vänster
            if (event.values[0] < 0.4f && event.values[0] > -0.4f && isFullStepTakenX) {
                isFullStepTakenX = false;
                forwardX = false;
                backwardX = false;
                forwardY = false;
                backwardY = false;
            }
            if (isFullStepTakenX) {
                return;
            }
            if (event.values[1] < 0.4f && event.values[1] > -0.4f && isFullStepTakenY) {
                isFullStepTakenY = false;
                forwardX = false;
                backwardX = false;
                forwardY = false;
                backwardY = false;
            }
            if (isFullStepTakenY) {
                return;
            }
            if (event.values[0] > 1.0f) {
                lastForwardX = event.values[0];
                stepTakenX(lastForwardX);
            }
            if (event.values[0] < -1.0f) {
                lastBackwardX = event.values[0];
                stepTakenX(lastBackwardX);
            }
            if (event.values[1] > 1.0f) {
                lastForwardY = event.values[1];
                stepTakenY(lastForwardY);
            }
            if (event.values[1] < -1.0f) {
                lastBackwardY = event.values[1];
                stepTakenY(lastBackwardY);
            }
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            number = event.values[0];
            if (number < 1) {
                gameActivity.showTimer();
            }
            proxy = event.values[0] + " The only fkn value leggo";
//            gameActivity.setsProximity(proxy);
        }
    }

    public void stepTakenX(float value) {
        if (value < -1.0f) {
            backwardX = true;
        }

        if (value > 1.0f) {
            forwardX = true;
        }

        if (backwardX) {
            if (value > 1.0f) {
                gameActivity.moveForward();
//                Toast.makeText(gameActivity, "Step forward", Toast.LENGTH_SHORT).show();
                forwardX = false;
                backwardX = false;
                forwardY = false;
                backwardY = false;
                isFullStepTakenX = true;
            }
        }

        if (forwardX) {
            if (value < -1.0f) {
                gameActivity.moveDown();
//                Toast.makeText(gameActivity, "Step back!", Toast.LENGTH_SHORT).show();
                forwardX = false;
                backwardX = false;
                forwardY = false;
                backwardY = false;
                isFullStepTakenX = true;
            }
        }
    }

    public void stepTakenY(float value) {
        if (value < -1.0f) {
            backwardY = true;
        }

        if (value > 1.0f) {
            forwardY = true;
        }

        if (backwardY) {
            if (value > 1.0f) {
                gameActivity.moveLeft();
                forwardY = false;
                backwardY = false;
                forwardX = false;
                backwardX = false;
                isFullStepTakenY = true;
            }
        }

        if (forwardY) {
            if (value < -1.0f) {
                gameActivity.moveRight();
                forwardY = false;
                backwardY = false;
                forwardX = false;
                backwardX = false;
                isFullStepTakenY = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
