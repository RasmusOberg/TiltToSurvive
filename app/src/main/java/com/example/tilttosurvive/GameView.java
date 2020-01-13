package com.example.tilttosurvive;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;
    private Canvas canvas;

    private Paint paint;

    private Bitmap backgroundImage;
    private Bitmap monster;
    private Bitmap monster2;
    private Bitmap monster3;
    private Bitmap monster4;
    private Bitmap monster5;
    //    private Bitmap characterImage;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private long time;
    private boolean firstStep = false, showTimer = false;

    private String time10th, newTime, newTime2;

//    private Button btnMove;

    public GameView(Context context){
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.bakgrund4);
//        characterImage = BitmapFactory.decodeResource(getResources(), R.drawable.ninja2);
        backgroundImage.createScaledBitmap(backgroundImage,screenWidth,screenHeight,false);

        getHolder().addCallback(this);

        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(200);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


//        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.gubbe));
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.ninja2));
        monster = BitmapFactory.decodeResource(getResources(), R.drawable.monster);
        monster2 = BitmapFactory.decodeResource(getResources(), R.drawable.monster2);
        monster3 = BitmapFactory.decodeResource(getResources(), R.drawable.monster3);
        monster4 = BitmapFactory.decodeResource(getResources(), R.drawable.monster4);
        monster5 = BitmapFactory.decodeResource(getResources(), R.drawable.monster5);

        gameThread.setRunning(true);
        gameThread.start();

    }

    public void moveForward(){
        if(firstStep == false)
            startTimer();
        character.moveForward(canvas);
    }

    public void moveLeft(){
        if(firstStep == false)
            startTimer();
        character.moveLeft(canvas);
    }

    public void moveRight(){
        if(firstStep == false)
            startTimer();
        character.moveRight(canvas);
    }

    public void moveDown(){
        if(firstStep == false)
            startTimer();
        character.moveDown(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                gameThread.setRunning(false);
                gameThread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }
//
//    private class timerClass extends implements TimerTask{
//
//        @Override
//        public void run() {
//
//        }
//    }
//
    private void startTimer() {
        firstStep = true;
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        };
//
//        Timer timer = new Timer(timerTask, 1000l);
//
        time = System.currentTimeMillis();
    }

    public void toast(){
        Toast.makeText(getContext(), "Run metoden anropas haha =)", Toast.LENGTH_SHORT).show();
    }

    public void update(){
        character.update();

        // Här kommer Input från sensor tror jag hahahahahhahahahahaha =D

    }

    public void showTimer(boolean bool){
        showTimer = bool;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        canvas.drawBitmap(backgroundImage,0,0,null);
        canvas.drawBitmap(monster,50,50,null);
        canvas.drawBitmap(monster2, 150, 150, null);
        canvas.drawBitmap(monster3, 300, 300, null);
        canvas.drawBitmap(monster4, 400,400,null);
        canvas.drawBitmap(monster5, 600,600,null);

        time10th = String.valueOf(((System.currentTimeMillis() - time)/1000));

        if (showTimer) {
            canvas.drawText(time10th, 860, 150, paint);
            Log.d(TAG, "draw: " + showTimer);
        }

//        canvas.drawBitmap(characterImage, x, y, null);

        if(canvas!=null){
            character.draw(canvas);
        }

        //fuckgit
    }
}
