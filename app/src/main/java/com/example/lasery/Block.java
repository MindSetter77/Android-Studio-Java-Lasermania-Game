package com.example.lasery;

//


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Block {
    Context context;

    private double positionX;
    private double positionY;
    //Wielkość bloku
    private final double radius = 200;
    private int type;
    Paint paint;

    public Block(Context context, double positionX, double positionY, int type){
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        this.context = context;
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

    public boolean checkIfHit(double x, double y){
        boolean flag1 = false;
        boolean flag2 = false;


        if(x >= positionX && x<positionX+radius){
            flag1 = true;

        }

        if( y >= positionY && y<positionY+radius){
            flag2 = true;
        }

        if(flag1 && flag2){
            return true;
        } else {
            return false;
        }





    }


}
