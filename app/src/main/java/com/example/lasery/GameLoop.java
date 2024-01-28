package com.example.lasery;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;

    private boolean isRunning = false;
    private Game game;
    private SurfaceHolder surfaceHolder;
    private double averageUPS;
    private double averageFPS;
    LampTarget lampTarget;
    DisplayMetrics displayMetrics;
    public GameLoop(Game game, SurfaceHolder surfaceHolder, LampTarget lampTarget, DisplayMetrics displayMetrics){
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        this.lampTarget = lampTarget;
        this.displayMetrics = displayMetrics;
    }

    public double getAverageUPS(){
        return this.averageUPS;
    }

    public double getAverageFPS(){
        return this.averageFPS;
    }

    public void startLoop(){
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        //Game loop
        Canvas canvas;
        startTime = System.currentTimeMillis();
        //NIESKOŃCZONA PĘTLA
        while(isRunning ){

            try{
                canvas = surfaceHolder.lockCanvas();
                if(!lampTarget.getWin()){

                    game.update(); //Wykonuje game update która zmienia zmienne
                    game.draw(canvas); //Rysuje rozgrywkę
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } else {
                    lampTarget.drawWin(canvas, displayMetrics);
                }

            } catch (Exception ex){
                ex.printStackTrace();
            }

            updateCount++;
            frameCount++;

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000){
                averageUPS = updateCount/ (1E-3 * elapsedTime);
                averageFPS = frameCount/ (1E-3 * elapsedTime);
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }

        }
    }




}