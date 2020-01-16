package com.example.tilttosurvive;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.tv.TvInputManager;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import static android.content.ContentValues.TAG;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Character character;
    private Canvas canvas;
    private boolean firstStep = false, showTimer = false, showMonsters;
    private Paint paint;
    private Bitmap backgroundImage;
    private Long time;
    private String time10th;
    private MediaPlayer soundDead;
    public Bitmap finishPoint;
    private String m_Text = "";
    private ArrayList<Monster> monsterList = new ArrayList<>();
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Repo repo;

    public GameView(Context context, Application application) {
        super(context);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.bakgrund4);
        backgroundImage.createScaledBitmap(backgroundImage, screenWidth, screenHeight, false);
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(200);
        soundDead = MediaPlayer.create(context, R.raw.dead);
        repo = new Repo(application);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawCharacter();
        drawMonsters();

        gameThread.setRunning(true);
        gameThread.start();
    }

    public void drawCharacter(){
        character = new Character(BitmapFactory.decodeResource(getResources(), R.drawable.ninja2));
        finishPoint = BitmapFactory.decodeResource(getResources(), R.drawable.castle);
    }

    public void drawMonsters(){
        Monster monster1 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster), 320, 2350);
        monsterList.add(monster1);
        Monster monster2 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster5), 910, 2055);
        monsterList.add(monster2);
        Monster monster3 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster3), 615, 1760);
        monsterList.add(monster3);
        Monster monster4 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster4), 1205, 1465);
        monsterList.add(monster4);
        Monster monster5 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster), 910, 285);
        monsterList.add(monster5);
        Monster monster6 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster5), 615, 1170);
        monsterList.add(monster6);
        Monster monster7 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster3), 25, 1170);
        monsterList.add(monster7);
        Monster monster8 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster4), 320, 580);
        monsterList.add(monster8);
        Monster monster9 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster5), 320, -10);
        monsterList.add(monster9);
        Monster monster10 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster3), 1205, 580);
        monsterList.add(monster10);
        Monster monster11 = new Monster(BitmapFactory.decodeResource(getResources(), R.drawable.monster4), 25, 2055);
        monsterList.add(monster11);
    }

    public void moveForward() {
        if (firstStep == false) {
            startTimer();
        }
        character.moveForward(canvas);
        checkChar();
    }

    public void moveLeft() {
        if (firstStep == false) {
            startTimer();
        }
        character.moveLeft(canvas);
        checkChar();
    }

    public void moveRight() {
        if (firstStep == false) {
            startTimer();
        }
        character.moveRight(canvas);
        checkChar();
    }

    public void moveDown() {
        if (firstStep == false) {
            startTimer();
        }
        character.moveDown(canvas);
        checkChar();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void checkChar() {
        for (int i = 0; i < monsterList.size(); i++) {
            if (character.getX() == monsterList.get(i).getX() && character.getY() == monsterList.get(i).getY()) {
                soundDead.start();
                Toast.makeText(getContext(), "DEAD!!!! hahahahaah", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getContext(), MainActivity.class);
                gameThread.setRunning(false);
                getContext().startActivity(i2);
            }
        }
        if (character.getX() == 25 && character.getY() == -10) {
            character.setAbleToMove(false);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Highscore! =D Enter Name");

            ArrayList<Score> list = (ArrayList<Score>) repo.getHighscores();
            Log.w("TEST123","SCORE: " + time10th  + ", double value: " + Double.parseDouble(time10th));
            Log.w("TEST123","List(2) " + list.get(2).getScore());


            if (list.get(2).getScore() > Double.parseDouble(time10th)) {
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        Log.d(TAG, "onClick: " + m_Text + " " + time10th);
                        repo.insert(new Score(m_Text, Double.parseDouble(time10th)));
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        gameThread.setRunning(false);
                        getContext().startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        gameThread.setRunning(false);
                        getContext().startActivity(intent);
                    }
                });
                builder.show();
            }

        }
    }

    public void update() {
        character.update();
    }

    public void showMonsters(boolean bool){
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                showMonsters = false;
            }

        }.start();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        character.draw(canvas);

        if(showMonsters) {
            for (int i = 0; i < monsterList.size(); i++) {
                monsterList.get(i).draw(canvas);
            }
        }

        canvas.drawBitmap(finishPoint,25,-10,null);
        time10th = String.valueOf(((System.currentTimeMillis() - time) / 1000));

        if (showTimer) {
            canvas.drawText(time10th, 860, 150, paint);
        }

    }

    private void startTimer() {
        firstStep = true;
        time = System.currentTimeMillis();
    }

    public void showTimer(boolean bool) {
        showTimer = bool;
    }



}
