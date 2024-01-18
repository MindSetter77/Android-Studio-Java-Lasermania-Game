package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;

    private double velocityX;
    private double velocityY;
    BlockManager blockManager;
    Context context;
    public Player(Context context, double positionX, double positionY, double radius, BlockManager blockManager){
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.blockManager = blockManager;
        this.context = context;

        this.paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas){

        //Gracz jest kropką

        //canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);

        //Gracz jest kwadratem

        // Współrzędne lewego górnego rogu kwadratu
        float left = (float)positionX;
        float top = (float)positionY;

        // Współrzędne prawego dolnego rogu kwadratu (załóżmy, że kwadrat ma taki sam rozmiar jak promień)
        float right = (float)positionX+150;
        float bottom = (float)positionY + 150;

        Paint paint1 = new Paint();
        int color1 = ContextCompat.getColor(context, R.color.human);
        paint1.setColor(color1);

        // Narysuj kwadrat
        canvas.drawRect(left+20, top, right-20, bottom, paint);
        canvas.drawRect(left, top, left+20, bottom-50, paint1);
        canvas.drawRect(right-20, top, right, bottom-50, paint1);
        canvas.drawRect(left, bottom, right, bottom-20, paint1);
        canvas.drawCircle(left+75, top+75, 35, paint1);
        canvas.drawRect(left, top+70, right, bottom-70, paint1);

    }

    public void update(Joystick joystick){
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        boolean flag = false;
        
        boolean rog1 = false;
        boolean rog2 = false;
        boolean rog3 = false;
        boolean rog4 = false;

        for(Block blk : blockManager.blockList){
            if(blk.checkIfHit(positionX+150+velocityX, positionY+150+velocityY)){
                flag=true;
                rog3 = true;
                int color = ContextCompat.getColor(context, R.color.green);
                paint.setColor(color);
            }

            if(blk.checkIfHit(positionX+150+velocityX, positionY+velocityY)){
                flag=true;
                rog2 = true;
                

            }

            if(blk.checkIfHit(positionX+velocityX, positionY+velocityY)){
                flag=true;
                rog1 = true;
                

            }

            if(blk.checkIfHit(positionX+velocityX, positionY+150+velocityY)){
                flag=true;
                rog4 = true;
                
            }


        }

        if(flag){
            if(blockManager.checkIfOnBlock(positionX, positionY+155) || blockManager.checkIfOnBlock(positionX+150, positionY+155)){
                positionX += velocityX;
            } else {
                if((rog1 && rog2) || (rog1 && blockManager.checkIfOnBlock(positionX, positionY-5))){
                    positionX+=velocityX;
                } else if (rog2 && blockManager.checkIfOnBlock(positionX+150, positionY-5)) {
                    positionX+=velocityX;
                } else if ((rog2 && rog3) || (rog2 &&blockManager.checkIfOnBlock(positionX+155+velocityX, positionY+velocityY))) {
                    positionY+=velocityY;
                } else if (rog3 && blockManager.checkIfOnBlock(positionX+155+velocityX, positionY+150+velocityY)) {
                    positionY+=velocityY;
                } else if (rog4 && rog1) {
                    positionY += velocityY;
                } else if (rog4 && blockManager.checkIfOnBlock(positionX+velocityX-5, positionY+150+velocityY)) {
                    positionY += velocityY;
                } else if (rog1 && blockManager.checkIfOnBlock(positionX-5, positionY)) {
                    positionY += velocityY;
                }
            }



        } else {
            positionX += velocityX;
            positionY += velocityY;
        }

    }

    public void setPosition(double positionX, double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
