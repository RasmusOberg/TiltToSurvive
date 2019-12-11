package com.example.tilttosurvive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;

    private Bitmap backgroundImage;
    private Bitmap characterImage;

    private int x = 1205; //285
    private int y = 2060; //290

//    private Button btnMove;

    public GameView(Context context){
        super(context);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.bakgrund2);
        characterImage = BitmapFactory.decodeResource(getResources(), R.drawable.ninja2);
        backgroundImage.createScaledBitmap(backgroundImage,500,500,false);

        getHolder().addCallback(this);

        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


//        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.gubbe));
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.ninja2));

        gameThread.setRunning(true);
        gameThread.start();

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


    public void toast(){
        Toast.makeText(getContext(), "Run metoden anropas haha =)", Toast.LENGTH_SHORT).show();
    }

    public void update(){
        character.update();

        // Här kommer Input från sensor tror jag hahahahahhahahahahaha =D

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(backgroundImage,0,0,null);
//        canvas.drawBitmap(characterImage, x, y, null);

        if(canvas!=null){
            character.draw(canvas);
        }
    }
}
