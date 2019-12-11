package com.example.tilttosurvive;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class SensorListener implements SensorEventListener {

    MainActivity mainActivity;

    String acc = "", gyro = "", proxy = "";
    private float lastForwardX, lastBackwardX, lastForwardY, lastBackwardY;

    boolean forwardX = false, backwardX = false, forwardY = false, backwardY = false;
    boolean isFullStepTakenX = false, isFullStepTakenY = false;


    public SensorListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            acc = event.values[0] + " X value \n" +
                    event.values[1] + " Y value \n" +
                    event.values[2] + " Z value \n" +
                    "ACCCCCCCCC";
            mainActivity.setsAcc(acc);
        }

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){

//            x är fram och tillbaka
//            y är höger och vänster

            if(event.values[0] < 0.3f && event.values[0] > -0.3f){
                isFullStepTakenX = false;
            }

            if(isFullStepTakenX){
                return;
            }

            if(event.values[1] < 0.3f && event.values[1] > -0.3f){
                isFullStepTakenY = false;
            }

            if(isFullStepTakenY){
                return;
            }

            if(event.values[0] > 1.5f)  {
                lastForwardX = event.values[0];
//                gyro = event.values[0] + " X value \n" +
//                        event.values[1] + " Y value \n" +
//                        event.values[2] + " Z value \n"
//                        + lastForwardX + "LASTSENSE";
//                mainActivity.setsGyroscope(gyro);
                stepTakenX(lastForwardX);
            }
            if(event.values[0] < -1.5f){
                lastBackwardX = event.values[0];
                stepTakenX(lastBackwardX);
            }

            if(event.values[1] > 1.5f)  {
                lastForwardY = event.values[1];

                stepTakenY(lastForwardY);
            }
            if(event.values[1] < -1.5f){
                lastBackwardY = event.values[1];
                stepTakenY(lastBackwardY);
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){

            proxy = event.values[0] + " The only fkn value leggo";
            if(event.values[0] == 0){
                proximityMethod(event.values[0]);
            }
            mainActivity.setsProximity(proxy);
        }
    }

    private void proximityMethod(float value){

        Toast.makeText(mainActivity, "Proximity SENSOR IS FKN DARK DO SOMTHING", Toast.LENGTH_SHORT).show();
//        doSomething();
    }

    public void stepTakenX(float value){


        if(value < -1.5f){
            backwardX = true;
        }

        if(value > 1.5f){
            forwardX = true;
        }


        if(backwardX) {
            if (value > 1.5f) {
                Toast.makeText(mainActivity, "Step forward", Toast.LENGTH_SHORT).show();
                forwardX = false;
                backwardX = false;
                isFullStepTakenX = true;
            }
        }

        if(forwardX) {
            if (value < -1.5f) {
                Toast.makeText(mainActivity, "Step back!", Toast.LENGTH_SHORT).show();
                forwardX = false;
                backwardX = false;
                isFullStepTakenX = true;
            }
        }
    }

    public void stepTakenY(float value){


        if(value < -1.5f){
            backwardY = true;
        }

        if(value > 1.5f){
            forwardY = true;
        }


        if(backwardY) {
            if (value > 1.5f) {
                Toast.makeText(mainActivity, "Step left", Toast.LENGTH_SHORT).show();
                forwardY = false;
                backwardY = false;
                isFullStepTakenY = true;
            }
        }

        if(forwardY) {
            if (value < -1.5f) {
                Toast.makeText(mainActivity, "Step right!", Toast.LENGTH_SHORT).show();
                forwardY = false;
                backwardY = false;
                isFullStepTakenY = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
