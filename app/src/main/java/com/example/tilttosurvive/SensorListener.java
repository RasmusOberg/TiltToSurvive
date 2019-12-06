package com.example.tilttosurvive;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class SensorListener implements SensorEventListener {

    MainActivity mainActivity;

    String acc = "", gyro = "", proxy = "";
    private float lastForward, lastBackward;

    boolean forward = false, backward = false, backFirst, forwardFirst;


    public SensorListener(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            acc = event.values[0] + " X value \n" +
                    event.values[1] + " Y value \n" +
                    event.values[2] + " Z value \n";
            mainActivity.setsAcc(acc);
        }

        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){

//            x är fram och tillbaka
//            y är höger och vänster



            if(event.values[0] > 1.5f)  {
                lastForward = event.values[0];
                gyro = event.values[0] + " X value \n" +
                        event.values[1] + " Y value \n" +
                        event.values[2] + " Z value \n"
                        + lastForward + "LASTSENSE";
                mainActivity.setsGyroscope(gyro);
//                Toast.makeText(mainActivity, "Tilt forward detected!" , Toast.LENGTH_SHORT).show();
                stepTaken(lastForward);
            }
            if(event.values[0] < -1.5f){
//                Toast.makeText(mainActivity, "Tilt backward detected!" , Toast.LENGTH_SHORT).show();
                lastBackward = event.values[0];
                stepTaken(lastBackward);
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){

            proxy = event.values[0] + " The only fkn value leggo";
            mainActivity.setsProximity(proxy);
        }
    }

    public void stepTaken(float value){
        if(value < -1.5f){
            backward = true;
            backFirst = true;
        }

        if(value > 1.5f){
            forward = true;
            forwardFirst = true;
        }

        if(backFirst) {
            if (forward) {
                Toast.makeText(mainActivity, "Step back", Toast.LENGTH_SHORT).show();
                forward = false;
                backward = false;
                backFirst = false;
                forwardFirst = false;
            }
        }else if(forwardFirst) {
            if (backward) {
                Toast.makeText(mainActivity, "Step forward!", Toast.LENGTH_SHORT).show();
                forward = false;
                backward = false;
                backFirst = false;
                forwardFirst = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
