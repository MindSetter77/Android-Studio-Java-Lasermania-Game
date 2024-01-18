package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Lamp {
    double positionX;
    double positionY;

    double laserStartX;
    double laserStartY;
    double radius;
    Context context;
    BlockManager blockManager;
    double a;
    double b;
    public Lamp(Context context, double positionX, double positionY, BlockManager blockManager){
        this.positionX = positionX;
        this.positionY = positionY;
        this.context = context;
        this.blockManager = blockManager;


        this.radius = 200;

        laserStartX = positionX + (radius / 2);
        laserStartY = positionY + (radius / 2);
    }

    public void drawLamp(Canvas canvas){
        Paint paint1 = new Paint();
        int color1 = ContextCompat.getColor(context, R.color.human);
        paint1.setColor(color1);

        Paint paintGold = new Paint();
        int colorGold = ContextCompat.getColor(context, R.color.golden);
        paintGold.setColor(colorGold);
        paintGold.setStrokeWidth(20);

        Paint paintGreen = new Paint();
        int colorLaser = ContextCompat.getColor(context, R.color.greenLaser);
        paintGreen.setColor(colorLaser);


        Paint paintBlack = new Paint();
        int colorBlack = ContextCompat.getColor(context, R.color.black);
        paintBlack.setColor(colorBlack);




        // Narysuj kwadrat
        canvas.drawRect((float)positionX, (float)positionY, (float)(positionX+radius), (float)(positionY+radius), paintGold);
        canvas.drawRect((float)(positionX+10), (float)(positionY+10), (float)(positionX+radius-10), (float)(positionY+radius-10), paintBlack);
        canvas.drawLine((float)(positionX+10), (float) (positionY+radius-10), (float)(positionX+(radius/2)), (float) (positionY + (radius/2)), paintGold);
        canvas.drawLine((float)(positionX+radius-10), (float) (positionY+radius-10), (float)(positionX+(radius/2)), (float) (positionY + (radius/2)), paintGold);
        canvas.drawLine((float)(positionX+(radius/2)), (float) (positionY), (float)(positionX+(radius/2)), (float) (positionY + (radius/2)), paintGold);
        canvas.drawCircle((float) (positionX + (radius/2)), (float) (positionY + (radius/2)), 65, paintGold);
        canvas.drawCircle((float) (positionX + (radius/2)), (float) (positionY + (radius/2)), 45, paintGreen);
    }

    public void drawLaser(Canvas canvas){
        Paint paintGreen = new Paint();
        int colorLaser = ContextCompat.getColor(context, R.color.greenLaser);
        paintGreen.setColor(colorLaser);
        paintGreen.setStrokeWidth(25);

        Paint paintRed = new Paint();
        int colorRed = ContextCompat.getColor(context, R.color.red);
        paintRed.setColor(colorRed);
        paintRed.setStrokeWidth(25);

        a = laserStartX;
        b = laserStartY;


        boolean xChange = false;
        boolean yChange = false;


        double x = a;
        double y = b;


        //getline z mod 1
        getLine(positionX, positionY, 1);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  paintGreen);

        //getline z mod 2

        x = a;
        y = b;
        getLine(x+1, y, 2);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  paintGreen);
        //getline z mod 3
        x = a;
        y = b;
        getLine(x, y+1, 3);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  paintGreen);

        //getline z mod 4

        x = a;
        y = b;
        getLine(x-1, y, 4);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  paintGreen);


    }


    public void getLine(double x, double y, int mode){
        boolean flag = true;

        while(flag){
            switch (mode){
                case 1:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                    } else {
                        x--;
                        y--;
                        if(x<=0 || y<=0){
                            flag=false;
                        }
                    }
                    break;
                case 2:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                    } else {
                        x++;
                        y--;
                        if(x>=1400 || y<=0){
                            flag=false;
                        }
                    }
                    break;
                case 3:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                    } else {
                        x++;
                        y++;
                        if(x>=1400 || y>=2600){
                            flag=false;
                        }
                    }
                    break;
                case 4:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                    } else {
                        x--;
                        y++;
                        if(x<=0 || y>=2600){
                            flag=false;
                        }
                    }
                    break;
            }
        }

        a = x;
        b = y;
    }
}
