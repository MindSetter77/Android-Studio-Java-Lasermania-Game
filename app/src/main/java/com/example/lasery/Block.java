package com.example.lasery;

//


import static com.example.lasery.Player.MAX_SPEED;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Block {
    Context context;

    public double positionX;
    public double positionY;
    public double velocityX;
    public double velocityY;

    public final double radius = 200;
    private int type;
    Paint paint;
    private boolean movable;

    public Block(Context context, double positionX, double positionY, int type, boolean movable){
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        this.context = context;
        this.movable = movable;
        this.paint = new Paint();
    }


    public void draw(Canvas canvas, GameDisplay gameDisplay){

        this.paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);

        Paint paint1 = new Paint();
        int color1 = ContextCompat.getColor(context, R.color.green);
        paint1.setColor(color1);

        Paint paint2 = new Paint();
        int color2 = ContextCompat.getColor(context, R.color.rudy);
        paint2.setColor(color2);

        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX), gameDisplay.gameToDisplayCoordinatesY((float) positionY), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paint);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius - 30)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + 30)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paint2);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius - 30)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paint2);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + 30)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + 30)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius - 30)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius - 30)), paint1);
    }

    public boolean checkIfHit(double x, double y) {
        return x >= positionX && x < positionX + radius && y >= positionY && y < positionY + radius;
    }

    public boolean isMovable() {
        return movable;
    }

    public void move(double deltaX, double deltaY) {
        if (movable) {
            positionX += deltaX;
            positionY += deltaY;
        }
    }
}
