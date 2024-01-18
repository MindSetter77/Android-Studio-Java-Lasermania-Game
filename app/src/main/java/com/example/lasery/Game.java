package com.example.lasery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private Lamp lamp;

    GameLoop gameLoop;
    Context context;
    BlockManager blockManager;


    private final Joystick joystick;

    public Game(Context context){
        super(context);
        this.context = context;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.blockManager = new BlockManager(getContext());
        blockManager.createBlocks(1);

        this.player = new Player(getContext(), 600, 600, 30, blockManager);
        this.lamp = new Lamp(getContext(),1200, 1900, blockManager);




        this.joystick = new Joystick(1150, 2500, 130, 70);
        this.gameLoop = new GameLoop(this, surfaceHolder);
        setFocusable(true);
    }

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

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        joystick.draw(canvas);
        lamp.drawLamp(canvas);
        lamp.drawLaser(canvas);
        blockManager.drawBlocks(canvas);
        player.draw(canvas);

    }

    public void drawUPS(Canvas canvas){
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(80);
        canvas.drawText("UPS: "+averageUPS, 100, 70, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        paint.setTextSize(80);
        canvas.drawText("FPS: "+averageFPS, 100, 140, paint);
    }

    public void update(){
        joystick.update();
        player.update(joystick);
    }
}
