package com.example.lasery;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.reflect.ParameterizedType;

//Joystick odpowiedzialny za poruszanie się gracza

public class Joystick {
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    private boolean isPressed;
    private int outerCircleRadius;
    private int innerCircleRadius;
    private int outerCircleCenterPositionX;
    private int outerCircleCenterPositionY;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;
    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius){
        this.outerCircleCenterPositionX = centerPositionX;
        this.outerCircleCenterPositionY = centerPositionY;
        this.innerCircleCenterPositionX = centerPositionX;
        this.innerCircleCenterPositionY = centerPositionY;

        this.isPressed = false;

        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    public void draw(Canvas canvas){
        canvas.drawCircle(outerCircleCenterPositionX, outerCircleCenterPositionY, outerCircleRadius, outerCirclePaint);
        canvas.drawCircle(innerCircleCenterPositionX, innerCircleCenterPositionY, innerCircleRadius, innerCirclePaint);

    }

    public void update(){
        updateInnerCirclePosition();
    }

    public void updateInnerCirclePosition(){
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);

    }

    public boolean isPressed(double x, double y){
        joystickCenterToTouchDistance = Math.sqrt(Math.pow(outerCircleCenterPositionX - x, 2) +
                Math.pow(outerCircleCenterPositionY - y, 2));
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(Boolean isPressed){
        this.isPressed = isPressed;
    }

    public boolean getIsPressed(){
        return this.isPressed;
    }

    public void setActuator(double x, double y){
        double dX = x - outerCircleCenterPositionX;
        double dY = y - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        if(deltaDistance < outerCircleRadius){
            actuatorX = dX/outerCircleRadius;
            actuatorY = dY/outerCircleRadius;
        } else {
            actuatorX = dX/deltaDistance;
            actuatorY = dY/deltaDistance;
        }
    }

    public void resetActuator(){
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX(){
        return this.actuatorX;
    }

    public double getActuatorY(){
        return this.actuatorY;
    }
}
