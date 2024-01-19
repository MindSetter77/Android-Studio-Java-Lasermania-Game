package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Player {
    private static final double SPEED_PIXELS_PER_SECOND = 400.0;
    public static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;

    private double positionX;
    private double positionY;
    private double radius;
    private Paint paint;

    private double velocityX;
    private double velocityY;
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

    public void draw(Canvas canvas) {

        //Gracz jest kropką

        //canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);

        //Gracz jest kwadratem

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
        canvas.drawRect(left + 20, top, right - 20, bottom, paint);
        canvas.drawRect(left, top, left + 20, bottom - 50, paint1);
        canvas.drawRect(right - 20, top, right, bottom - 50, paint1);
        canvas.drawRect(left, bottom, right, bottom - 20, paint1);
        canvas.drawCircle(left + 75, top + 75, 35, paint1);
        canvas.drawRect(left, top + 70, right, bottom - 70, paint1);

    }

    public void update(Joystick joystick) {
        boolean canMove = true;

        for (Block blk : blockManager.blockList) {
            double nextX = positionX + joystick.getActuatorX() * MAX_SPEED;
            double nextY = positionY + joystick.getActuatorY() * MAX_SPEED;

            if (blk.checkIfHit(nextX + 150, nextY + 150) ||
                    blk.checkIfHit(nextX, nextY + 150) ||
                    blk.checkIfHit(nextX + 150, nextY) ||
                    blk.checkIfHit(nextX, nextY)) {
                canMove = false;
                handleMovableBlock(blk, joystick.getActuatorX() * MAX_SPEED, joystick.getActuatorY() * MAX_SPEED);
            }
        }
        if (canMove) {
            positionX += joystick.getActuatorX() * MAX_SPEED;
            positionY += joystick.getActuatorY() * MAX_SPEED;
        }
    }


    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void handleMovableBlock(Block block, double deltaX, double deltaY) {
        boolean move = true;

        if (block.isMovable()) {
            // Nowe współrzędne bloku ruchomego po przesunięciu przez gracza
            double nextX = block.positionX + deltaX;
            double nextY = block.positionY + deltaY;

            // Sprawdź kolizję z blokami na podstawie nowych współrzędnych
            for (Block otherBlock : blockManager.blockList) {
                // Pomijaj ten sam blok
                if (otherBlock.equals(block)) {
                    continue;
                }

                if (otherBlock.isMovable()) {
                    // Sprawdź kolizję z blokiem ruchomym
                    if (otherBlock.checkIfHit(nextX + 150, nextY + 150) ||
                            otherBlock.checkIfHit(nextX, nextY + 150) ||
                            otherBlock.checkIfHit(nextX + 150, nextY) ||
                            otherBlock.checkIfHit(nextX, nextY)) {

                        // Ustaw prędkość bloku na 0, aby zatrzymać ruch
                        block.velocityX = 0;
                        block.velocityY = 0;

                        // Jeśli blok ruchomy zetknie się z blokiem ruchomym, przesuń go wzdłuż bloku
                        double moveDeltaX = otherBlock.positionX - block.positionX;
                        double moveDeltaY = otherBlock.positionY - block.positionY;

                        block.move(moveDeltaX, moveDeltaY);

                        // Ustaw zmienną move na false, aby zapobiec dalszemu przesuwaniu
                        move = false;
                        break;
                    }
                } else {
                    // Sprawdź kolizję z blokiem nieruchomym
                    if (otherBlock.checkIfHit(nextX + 150, nextY + 150) ||
                            otherBlock.checkIfHit(nextX, nextY + 150) ||
                            otherBlock.checkIfHit(nextX + 150, nextY) ||
                            otherBlock.checkIfHit(nextX, nextY)) {

                        // Ustaw prędkość bloku na 0, aby zatrzymać ruch
                        block.velocityX = 0;
                        block.velocityY = 0;

                        // Ustaw zmienną move na false, aby zapobiec dalszemu przesuwaniu
                        move = false;
                        break;  // Przerwij pętlę, gdy wystąpi kolizja z blokiem nieruchomym
                    }
                }
            }

            // Jeśli można się przesunąć, przesuń blok
            if (move) {
                block.move(deltaX, deltaY);
            }
        }
    }
}

