package com.example.tilttosurvive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.BitSet;

public class Character {
    private Bitmap ninja;
    private int x2 = 1205;
    private int y2 = 2350;
    private int moveX = 295;
    private int moveY = 295;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean ableToMove;

    public Character(Bitmap ninja) {
        this.ninja = ninja;
        ableToMove = true;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(ninja, x2, y2, null);
    }

    public void setAbleToMove(boolean bool){
        ableToMove = bool;
    }

    public void update() {
        if (x2 < 0 && y2 < 0) {
            x2 -= moveX;
            y2 -= moveY;
        }
    }


    public void moveForward(Canvas canvas) {
        if (ableToMove) {

            if (y2 > 30) {
                y2 = y2 - moveY;
                canvas.drawBitmap(ninja, x2, y2, null);
//            Log.w("TEST123", "moveForward x2 = " + x2 + " y2 = " + y2);
                Log.w("TEST123", "ninja  widht = " + ninja.getWidth() + " height = " + ninja.getHeight());

            }
        }

    }

    public void moveLeft(Canvas canvas) {

        if(ableToMove) {


            x2 = x2 - moveX;
            canvas.drawBitmap(ninja, x2, y2, null);
//        Log.w("TEST123", "moveLeft x2 = " + x2 + " y2 = " + y2);

            if (x2 < 25) {
                x2 = 1205;
                canvas.drawBitmap(ninja, x2, y2, null);
            }

            Log.w("TEST123", "ninja  widht = " + ninja.getWidth() + " height = " + ninja.getHeight());

        }

    }

    public void moveRight(Canvas canvas) {
        if(ableToMove) {
            x2 = x2 + moveX;
            canvas.drawBitmap(ninja, x2, y2, null);
            if (x2 > 1205) {
                x2 = 25;
                canvas.drawBitmap(ninja, x2, y2, null);
            }

            Log.w("TEST123", "moveRight x2 = " + x2 + " y2 = " + y2);
            Log.w("TEST123", "ninja  widht = " + ninja.getWidth() + " height = " + ninja.getHeight());
        }
    }

    public void moveDown(Canvas canvas) {
        if(ableToMove) {
            if (y2 < 2350) {
                y2 = y2 + moveY;
                canvas.drawBitmap(ninja, x2, y2, null);
                Log.w("TEST123", "moveDown x2 = " + x2 + " y2 = " + y2);
                Log.w("TEST123", "ninja  widht = " + ninja.getWidth() + " height = " + ninja.getHeight());
            }
        }
    }

    public int getX() {
        return x2;
    }

    public int getY() {
        return y2;

    }
}
