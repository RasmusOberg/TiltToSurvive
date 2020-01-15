package com.example.tilttosurvive;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Monster {
    private Bitmap monster;
    private int x;
    private int y;

    private int moveX = 295;
    private int moveY = 295;

    public Monster(Bitmap monster, int x, int y ){
        this.monster = monster;
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(monster, x, y, null);
    }

    public Bitmap getMonster() {
        return monster;
    }

    public void setMonster(Bitmap monster) {
        this.monster = monster;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
