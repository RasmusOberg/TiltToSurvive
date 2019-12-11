package com.example.tilttosurvive;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.BitSet;

public class Character {
    private Bitmap ninja;
    private int x2 = 1205;
    private int y2 = 2350;

    private int moveX = 285;
    private int moveY = 290;
//    private Bitmap image;
//    private int x, y;
//    private int xVelocity = 10;
//    private int yVelocity = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Character(Bitmap ninja){
        this.ninja = ninja;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(ninja, x2, y2, null);
    }

    public void update(){
        if(x2<0 && y2<0){
            x2 -= moveX;
            y2 -= moveY;
        }
        //
//        if(x2<0 && y2<0){
//            x2 = screenWidth / 2;
//            y2 = screenHeight / 2;
//        }
//        else {
//            x2 -= moveX;
//            y2 -= moveY;
//            if(x2 > screenWidth - ninja.getWidth() || x2 < 0){
//                moveX = moveX *-1;
////                xVelocity = xVelocity*-1;
//            }
//            if(y2 > screenHeight - ninja.getHeight() || y2 < 0){
//                moveY = moveY *-1;
////                yVelocity = yVelocity*-1;
//            }
//        }
    }



//    public Character(Bitmap bitMap){
//       this.image = bitMap;
//       x = 100;
//       y= 100;
//    }
//
//    public void draw (Canvas canvas){
//        canvas.drawBitmap(image, x, y, null);
//    }
//
//    public void update(){
//        if(x<0 && y<0){
//            x = screenWidth / 2;
//
//            y = screenHeight / 2;
//        } else {
//            x+= xVelocity;
//            y += yVelocity;
//            if(x > screenWidth - image.getWidth() || x < 0){
//                xVelocity = xVelocity*-1;
//            }
//            if(y > screenHeight - image.getHeight() || y < 0){
//                yVelocity = yVelocity*-1;
//            }
//        }
//
//    }

}
