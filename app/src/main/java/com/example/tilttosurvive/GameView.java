package com.example.tilttosurvive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;
    private Bitmap backgroundImage;
    //    private Bitmap monster, monster2, monster3, monster4, monster5;
    private Character monster, monster2, monster3, monster4, monster5;
    //    private int monsterX, monsterY, monster2x, monster2y, monster3x, monster3y, monster4x, monster4y, monster5x, monster5y;
    //    private Bitmap characterImage;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
//    private Button btnMove;

    public GameView(Context context){
        super(context);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.bakgrund4);
//        characterImage = BitmapFactory.decodeResource(getResources(), R.drawable.ninja2);
        backgroundImage.createScaledBitmap(backgroundImage,screenWidth,screenHeight,false);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.gubbe));
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.ninja2));
        monster = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.monster));
        monster2 = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.monster2));
        monster3 = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.monster3));
        monster4 = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.monster4));
        monster5 = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.monster5));
//        monster = BitmapFactory.decodeResource(getResources(), R.drawable.monster);
//        monster2 = BitmapFactory.decodeResource(getResources(), R.drawable.monster2);
//        monster3 = BitmapFactory.decodeResource(getResources(), R.drawable.monster3);
//        monster4 = BitmapFactory.decodeResource(getResources(), R.drawable.monster4);
//        monster5 = BitmapFactory.decodeResource(getResources(), R.drawable.monster5);
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
    //


    public void toast(){
        Toast.makeText(getContext(), "Run metoden anropas haha =)", Toast.LENGTH_SHORT).show();
    }

    public void update(){
        character.update();
//        int x = character.getXPos();
//        int y = character.getYPos();

//        if(x == monsterX && y == monsterY){
//         gameOver();
//        }else if(x == monster2x && y == monster2y) {
//            gameOver();
//            // Här kommer Input från sensor tror jag hahahahahhahahahahaha =D
//        }
    }

    public void gameOver(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GAME OVER");
        builder.setMessage("100");
        builder.setPositiveButton("Try again!", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "New try!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(backgroundImage,0,0,null);
//        canvas.drawBitmap(monster,50,50,null);
//        canvas.drawBitmap(monster2, 150, 150, null);
//        canvas.drawBitmap(monster3, 300, 300, null);
//        canvas.drawBitmap(monster4, 400,400,null);
//        canvas.drawBitmap(monster5, 600,600,null);
//        canvas.drawBitmap(characterImage, x, y, null);


        character.draw(canvas);
    }
}
