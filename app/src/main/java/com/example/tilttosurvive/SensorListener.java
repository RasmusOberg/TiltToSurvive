package com.example.tilttosurvive;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.Toast;

public class SensorListener implements SensorEventListener {

    MainActivity mainActivity;

    String acc = "", gyro = "", proxy = "";
    private float lastSense;


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



            if(event.values[0] > 1.0f)  {
                lastSense = event.values[0];
                gyro = event.values[0] + " X value \n" +
                        event.values[1] + " Y value \n" +
                        event.values[2] + " Z value \n";
                mainActivity.setsGyroscope(gyro);

            }
            if(event.values[0] > lastSense - 2.0f){
                Toast.makeText(mainActivity, "Step taken!" , Toast.LENGTH_SHORT).show();
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){

            proxy = event.values[0] + " The only fkn value leggo";
            mainActivity.setsProximity(proxy);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
