package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
    /*

    Funkcja odpowiedzialna na rysowanie lampy (Obiekt z którego wychodzi laser),
    oraz lasera który z niej wychodzi

     */

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
    private LampTarget lampTarget;



    public Lamp(Context context, double positionX, double positionY, BlockManager blockManager, LampTarget lampTarget){
        this.positionX = positionX;
        this.positionY = positionY;
        this.context = context;
        this.blockManager = blockManager;
        this.paintList = new ArrayList<>();
        this.lampTarget = lampTarget;
        this.radius = 200;

        laserStartX = positionX + (radius / 2);
        laserStartY = positionY + (radius / 2);
    }

    //Metoda rysująca lampę
    public void drawLamp(Canvas canvas, GameDisplay gameDisplay){
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


        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX), gameDisplay.gameToDisplayCoordinatesY((float) positionY), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paintGold);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) positionX), gameDisplay.gameToDisplayCoordinatesY((float) positionY), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius)), paintGold);
        canvas.drawRect(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + 10)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + 10)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius - 10)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius - 10)), paintBlack);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + 10)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius - 10)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), paintGold);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + radius - 10)), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + radius - 10)), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), paintGold);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) positionY), gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), paintGold);
        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), 65, paintGold);
        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX((float) (positionX + (radius / 2))), gameDisplay.gameToDisplayCoordinatesY((float) (positionY + (radius / 2))), 45, paintGreen);
    }

    //Metoda rysująca laser
    public void drawLaser(Canvas canvas, GameDisplay gameDisplay){
        Paint pp1 = new Paint();
        int colorLaser = ContextCompat.getColor(context, R.color.purpleLaser3);
        pp1.setColor(colorLaser);
        pp1.setStrokeWidth(30);

        Paint pp2 = new Paint();
        colorLaser = ContextCompat.getColor(context, R.color.purpleLaser7);
        pp2.setColor(colorLaser);
        pp2.setStrokeWidth(10);

        //zmienna globalne punktu kończącego laser (zmiana kierunku).
        //funkcja getLine wykonuje ich update.

        a = laserStartX;
        b = laserStartY;

        //zmienne punktu startu lasera

        double x = laserStartX;
        double y = laserStartY;

        //MODE - definiuje kierunek skrętu

        double mode = 1;

        //LASER MOVEMENT

        this.laserDrawStart = true;

        //Metoda getline z mode 1 (start lasera)
        mode = getLine(positionX, positionY, mode);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) x ),gameDisplay.gameToDisplayCoordinatesY((float) y), gameDisplay.gameToDisplayCoordinatesX((float) a), gameDisplay.gameToDisplayCoordinatesY((float) b),  pp1);
        canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) x) ,gameDisplay.gameToDisplayCoordinatesY((float) y), gameDisplay.gameToDisplayCoordinatesX((float) a), gameDisplay.gameToDisplayCoordinatesY((float) b),  pp2);

        int i = 0; //ilość zgięć lasera

        //rysowanie lasera w pętli (dopóki nie wyjdzie poza pole gry)
        while (laserDrawStart){
            x = a;
            y = b;
            mode = getLine(x, y, mode);
            canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) x) ,gameDisplay.gameToDisplayCoordinatesY((float) y), gameDisplay.gameToDisplayCoordinatesX((float) a), gameDisplay.gameToDisplayCoordinatesY((float) b),  pp1);
            canvas.drawLine(gameDisplay.gameToDisplayCoordinatesX((float) x) ,gameDisplay.gameToDisplayCoordinatesY((float) y), gameDisplay.gameToDisplayCoordinatesX((float) a), gameDisplay.gameToDisplayCoordinatesY((float) b) ,  pp2);
            i++;

        }


    }
    /*

    Metoda getline określa dokładne położenie gdzie będzie znajdował się laser.
    Zmienia zmienne a i b (końcowe x i y lasera) (pojedyńcze zgięcie) .


     */

    public double getLine(double x, double y, double mode){
        boolean flag = true; //Zmienia się na false jeżeli dochodzi do uderzenia w blok

        while(flag){
            switch ((int) mode){
                case 1:
                    if(blockManager.checkIfOnBlock(x, y)){ //Jeżeli uderzy w blok kończy się pętla
                        flag = false;
                        x++;
                        y++;
                    } else { //Brak kolizji z blokiem
                        if(lampTarget.checkIfTargetHit(x, y)){ //Trafienie laserem w cel
                            flag=false;
                            laserDrawStart = false;
                            lampTarget.win();
                        }
                        x--; //Zmiana wartości x
                        y--; //zmiana wartości y
                        if(x<=0 || y<=0){ //Wyjście poza teren gry
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
                        if(lampTarget.checkIfTargetHit(x, y)){
                            flag=false;
                            laserDrawStart = false;
                            lampTarget.win();
                        }
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
                        if(lampTarget.checkIfTargetHit(x, y)){
                            flag=false;
                            laserDrawStart = false;
                            lampTarget.win();
                        }
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
                        if(lampTarget.checkIfTargetHit(x, y)){
                            flag=false;
                            laserDrawStart = false;
                            lampTarget.win();
                        }
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


        //Zamiana x i y na zmienne globlane a i b

        a=x;
        b=y;

        //Zwrócenie odpowiedniego mode (kierunek w który będzie przemieszczał się laser)

        switch ((int)mode){
            case 1:
                if(blockManager.checkIfOnBlock(x-1, y)){ //Sprawdza z jakiej dokładnie strony laser
                    return 2;                //uderzył w blok aby zwrócić odpowiedni kierunek odbicia
                } else {
                    return 4;
                }
            case 2:
                if(blockManager.checkIfOnBlock(x, y-1)){
                    return 3;
                } else {
                    return 1;
                }
            case 3:
                if(blockManager.checkIfOnBlock(x+1, y)){
                    return 4;
                } else {
                    return 2;
                }
            case 4:
                if(blockManager.checkIfOnBlock(x-1, y)){
                    return 3;
                } else {
                    return 1;
                }
        }
        return 0;
    }
}