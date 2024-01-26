package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;
    BlockManager blockManager;
    Context context;

    public Player(Context context, double positionX, double positionY, double radius, BlockManager blockManager) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.blockManager = blockManager;
        this.context = context;

        this.paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.player);
        paint.setColor(color);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        // Współrzędne lewego górnego rogu kwadratu
        float left = (float) positionX;
        float top = (float) positionY;

        // Współrzędne prawego dolnego rogu kwadratu (załóżmy, że kwadrat ma taki sam rozmiar jak promień)
        float right = (float) positionX + 150;
        float bottom = (float) positionY + 150;

        Paint paint1 = new Paint();
        int color1 = ContextCompat.getColor(context, R.color.human);
        paint1.setColor(color1);

        // Narysuj kwadrat
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX(left + 20), gameDisplay.gameToDisplayCoordinatesY(top), gameDisplay.gameToDisplayCoordinatesX(right - 20), gameDisplay.gameToDisplayCoordinatesY(bottom), paint);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX(left), gameDisplay.gameToDisplayCoordinatesY(top), gameDisplay.gameToDisplayCoordinatesX(left + 20), gameDisplay.gameToDisplayCoordinatesY(bottom - 50), paint1);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX(right - 20), gameDisplay.gameToDisplayCoordinatesY(top), gameDisplay.gameToDisplayCoordinatesX(right), gameDisplay.gameToDisplayCoordinatesY(bottom - 50), paint1);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX(left), gameDisplay.gameToDisplayCoordinatesY(bottom), gameDisplay.gameToDisplayCoordinatesX(right), gameDisplay.gameToDisplayCoordinatesY(bottom - 20), paint1);
        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX(left + 75), gameDisplay.gameToDisplayCoordinatesY(top + 75), 35, paint1);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX(left), gameDisplay.gameToDisplayCoordinatesY(top + 70), gameDisplay.gameToDisplayCoordinatesX(right), gameDisplay.gameToDisplayCoordinatesY(bottom - 70), paint1);
    }

    public void update(Joystick joystick) {
        boolean canMove = true;
        double nextX = positionX + joystick.getActuatorX() * MAX_SPEED;
        double nextY = positionY + joystick.getActuatorY() * MAX_SPEED;

        for (Block blk : blockManager.blockList) {
            if (blk.checkIfHit(nextX + 150, nextY + 150) ||
                    blk.checkIfHit(nextX, nextY + 150) ||
                    blk.checkIfHit(nextX + 150, nextY) ||
                    blk.checkIfHit(nextX, nextY)) {
                if (blk.isMovable()) {
                    canMove = handleMovableBlock(blk, joystick.getActuatorX() * MAX_SPEED, joystick.getActuatorY() * MAX_SPEED);
                } else {
                    canMove = false;
                    break;
                }
            }
        }

        if (canMove) {
            positionX = nextX;
            positionY = nextY;
        }
    }


    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public boolean handleMovableBlock(Block block, double deltaX, double deltaY) {
        boolean moveX = true;
        boolean moveY = true;
        boolean canMove = true;

        if (block.isMovable()) {
            double nextX = block.positionX + deltaX;
            double nextY = block.positionY + deltaY;

            for (Block otherBlock : blockManager.blockList) {
                if (otherBlock.equals(block)) {
                    continue;
                }

                if (otherBlock.isMovable()) {
                    if (otherBlock.checkIfHit(nextX + 200, nextY + 200) ||
                            otherBlock.checkIfHit(nextX, nextY + 200) ||
                            otherBlock.checkIfHit(nextX + 200, nextY) ||
                            otherBlock.checkIfHit(nextX, nextY)) {
                        moveX = false;
                        moveY = false;
                        canMove = false;
                        break;
                    }
                } else {
                    if (otherBlock.checkIfHit(nextX + 200, nextY + 200) ||
                            otherBlock.checkIfHit(nextX, nextY + 200) ||
                            otherBlock.checkIfHit(nextX + 200, nextY) ||
                            otherBlock.checkIfHit(nextX, nextY)) {
                        if (deltaX != 0) {
                            moveX = false;
                            canMove = false;
                        }
                        if (deltaY != 0) {
                            moveY = false;
                            canMove = false;
                        }
                    }
                }
            }

            if (moveX && moveY) {
                block.move(deltaX, deltaY);
            }
        }

        return canMove;
    }

}
