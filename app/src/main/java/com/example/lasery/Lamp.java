package com.example.lasery;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

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
    boolean laserDrawStart;
    public ArrayList<Paint> paintList;



    public Lamp(Context context, double positionX, double positionY, BlockManager blockManager){
        this.positionX = positionX;
        this.positionY = positionY;
        this.context = context;
        this.blockManager = blockManager;
        this.paintList = new ArrayList<>();



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
        int colorLaser = ContextCompat.getColor(context, R.color.purpleLaser7);
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

        //Deklaracja kolorów

        Paint pp1 = new Paint();
        int colorLaser = ContextCompat.getColor(context, R.color.purpleLaser3);
        pp1.setColor(colorLaser);
        pp1.setStrokeWidth(30);


        Paint pp2 = new Paint();
        colorLaser = ContextCompat.getColor(context, R.color.purpleLaser7);
        pp2.setColor(colorLaser);
        pp2.setStrokeWidth(10);

        Paint paintGreen = new Paint();
        colorLaser = ContextCompat.getColor(context, R.color.greenLaser);
        paintGreen.setColor(colorLaser);
        paintGreen.setStrokeWidth(25);

        Paint paintRed = new Paint();
        int colorRed = ContextCompat.getColor(context, R.color.red);
        paintRed.setColor(colorRed);
        paintRed.setStrokeWidth(25);

        //zmienna globalne punktu kończącego laser (zmiana kierunku).
        //funkcja getLine wykonuje ich update.

        a = laserStartX;
        b = laserStartY;
        //zmienne punktu startu lasera początku lasera

        double x = laserStartX;
        double y = laserStartY;

        //MODE - definiuje kierunek skrętu
        double mode = 1;
        //LASER MOVEMENT
        this.laserDrawStart = true;

        //getline z mod 1
        mode = getLine(positionX, positionY, mode);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  pp1);
        canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  pp2);

        int i = 0;

        while (laserDrawStart){
            x = a;
            y = b;
            mode = getLine(x, y, mode);
            canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  pp1);
            canvas.drawLine((float) x ,(float) y, (float) a, (float) b,  pp2);
            i++;

        }


    }


    public double getLine(double x, double y, double mode){
        boolean flag = true;

        while(flag){
            switch ((int) mode){
                case 1:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                        x++;
                        y++;
                    } else {
                        x--;
                        y--;
                        if(x<=0 || y<=0){
                            flag=false;
                            laserDrawStart = false;
                        }
                    }
                    break;
                case 2:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                        x--;
                        y++;
                    } else {
                        x++;
                        y--;
                        if(x>=1400 || y<=0){
                            flag=false;
                            laserDrawStart = false;
                        }
                    }
                    break;
                case 3:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                        x--;
                        y--;
                    } else {
                        x++;
                        y++;
                        if(x>=1400 || y>=2600){
                            flag=false;
                            laserDrawStart = false;
                        }
                    }
                    break;
                case 4:
                    if(blockManager.checkIfOnBlock(x, y)){
                        flag = false;
                        x++;
                        y--;
                    } else {
                        x--;
                        y++;
                        if(x<=0 || y>=2600){
                            flag=false;
                            laserDrawStart = false;
                        }
                    }
                    break;
            }
        }

        //Ustawienie zmiennych globalnych
        a = x;
        b = y;

        double value = x%200;



        switch ((int)mode){
            case 1:
                if(blockManager.checkIfOnBlock(x-1, y)){
                    return 2;
                } else {
                    return 4;
                }
                /*

                if(value>185 || value < 15) {
                    return 2;
                } else {
                    return 4;
                }
                */
            case 2:

                if(blockManager.checkIfOnBlock(x, y-1)){
                    return 3;
                } else {
                    return 1;
                }

                /*
                if(value>185 || value < 15) {

                    return 1;
                } else {

                    return 3;
                }

                 */
            case 3:

                if(blockManager.checkIfOnBlock(x+1, y)){
                    return 4;
                } else {
                    return 2;
                }

                /*
                if(value>185 || value < 15) {

                    return 4;
                } else {

                    return 2;
                }

                 */
            case 4:

                if(blockManager.checkIfOnBlock(x-1, y)){
                    return 3;
                } else {
                    return 1;
                }

                /*
                if(value>185 || value < 15) {

                    return 3;
                } else {

                    return 1;
                }

                 */


        }
        return 0;

    }
}
