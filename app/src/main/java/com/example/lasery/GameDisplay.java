package com.example.lasery;

public class GameDisplay {

    Player player;

    double coordinateOffsetX;
    double coordinateOffsetY;
    double displayCenterX;
    double displayCenterY;
    double gameCenterX;
    double gameCenterY;

    public GameDisplay(Player player, int widthPixels, int heightPixels){
        this.player = player;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;


    }

    public void update(){
        gameCenterX = player.getPositionX();
        gameCenterY = player.getPositionY();

        this.coordinateOffsetX = displayCenterX - gameCenterX;
        this.coordinateOffsetY = displayCenterY - gameCenterY;
    }


    public float gameToDisplayCoordinatesX(float x){
        return x + (float)coordinateOffsetX;
    }

    public float gameToDisplayCoordinatesY(float y){
        return y + (float)coordinateOffsetY;
    }
}
