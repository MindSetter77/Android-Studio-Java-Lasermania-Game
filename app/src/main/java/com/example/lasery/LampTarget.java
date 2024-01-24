package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

//Obiekt do którego musi trafić laser

public class LampTarget {
    Context context;

    private double positionX;
    private double positionY;
    float radius;
    double centerX;
    double centerY;
    boolean won;

    public LampTarget(Context context, double positionX, double positionY){
        this.context = context;
        this.positionX = positionX;
        this.positionY = positionY;

        this.radius = 200;

        this.centerX = positionX + (radius/2);
        this.centerY = positionY + (radius/2);

        this.won = false;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        Paint paintGold = new Paint();
        int colorGold = ContextCompat.getColor(context, R.color.golden);
        paintGold.setColor(colorGold);
        paintGold.setStrokeWidth(20);

        Paint paintBlack = new Paint();
        int colorBlack = ContextCompat.getColor(context, R.color.black);
        paintBlack.setColor(colorBlack);
        paintBlack.setStrokeWidth(20);

        Paint paintGreen = new Paint();
        int colorLaser = ContextCompat.getColor(context, R.color.purpleLaser7);
        paintGreen.setColor(colorLaser);

        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX), gameDisplay.gameToDisplayCoordinatesY((float) positionY), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paintGold);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX+10), gameDisplay.gameToDisplayCoordinatesY((float) positionY+10), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius-10)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius-10)), paintBlack);
        paintGold.setStrokeWidth(10);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) positionX+1), gameDisplay.gameToDisplayCoordinatesY((float) positionY+1), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius-1)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius-1)), paintGold);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) (positionX+radius-1)), gameDisplay.gameToDisplayCoordinatesY((float) positionY+1), gameDisplay.gameToDisplayCoordinatesX((float) (positionX +1)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius-1)), paintGold);

        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), 65, paintGold);
        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), 45, paintGreen);
    }

    //Rysuje ekran wygranego poziomu
    public void drawWin(Canvas canvas, DisplayMetrics dm){
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE); // Kolor tekstu
        textPaint.setTextSize(100); // Zwiększ rozmiar tekstu na przykład na 72
        String text = "You won!";
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int textWidth = bounds.width();
        int textHeight = bounds.height();
        int x = (dm.widthPixels - textWidth) / 2;
        int y = (dm.heightPixels + textHeight) / 2;

        canvas.drawText(text, x, y, textPaint);
    }


    //Sprawdza czy cel zostal trafiony
    public boolean checkIfTargetHit(double x, double y){
        if(x >= centerX - 30 && x <= centerX + 30){
            return y>= centerY-30 && y <= centerY + 30;
        }
        return false;
    }

    //Funkcja wygrywająca gre
    public void win(){
        this.won = true;
    }

    public boolean getWin(){
        return this.won;
    }
}