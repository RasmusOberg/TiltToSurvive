package com.example.tilttosurvive;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(Boolean isRunning){
        running = isRunning;
    }

    @Override
    public void run() {

        while(running){
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
//                    this.gameView.toast();
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
