package com.example.lasery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
//Klasa jest głównym ekranem gry
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private LampTarget lampTarget;
    private Lamp lamp;

    GameLoop gameLoop;
    Context context;
    BlockManager blockManager;
    GameDisplay gameDisplay;
    DisplayMetrics displayMetrics;


    private final Joystick joystick;

    public Game(Context context){
        super(context);
        this.context = context;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.blockManager = new BlockManager(getContext());
        blockManager.createBlocks(1);
        this.displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.player = new Player(getContext(), displayMetrics.widthPixels/2.0, displayMetrics.heightPixels/2.0, 30, blockManager);
        this.lampTarget = new LampTarget(getContext(), 600, 2100);
        this.lamp = new Lamp(getContext(),1200, 1900, blockManager, lampTarget);

        this.gameDisplay = new GameDisplay(player, displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.joystick = new Joystick(displayMetrics.widthPixels-140, displayMetrics.heightPixels-130, 130, 70);
        this.gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);
    }

    //Do obslugi joysticka
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed((double)event.getX(), (double)event.getY())){
                    joystick.setIsPressed(true);
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    //Rozpocznij pętle
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }


    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    //Funkcja rysująca wszystkie elementy
    //Każdy element który wyświetla się na ekranie posiada funkcje draw
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        lampTarget.draw(canvas, gameDisplay);
        lamp.drawLamp(canvas, gameDisplay);
        lamp.drawLaser(canvas, gameDisplay);
        blockManager.drawBlocks(canvas, gameDisplay);
        player.draw(canvas, gameDisplay);

        if(lampTarget.won){
            lampTarget.drawWin(canvas, displayMetrics);
        } else {
            joystick.draw(canvas);
        }
        drawUPS(canvas);
        drawFPS(canvas);
    }

    //Rysuje UPS (Update per second)
    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(80);
        canvas.drawText("UPS: "+averageUPS, 100, 70, paint);
    }

    //Rysuje FPS (Frames per second)
    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(80);
        canvas.drawText("FPS: "+averageFPS, 100, 140, paint);
    }

    //Odświeża wszystkie wartości zmiennych
    public void update(){
        joystick.update();
        player.update(joystick);
        gameDisplay.update();
    }
}
