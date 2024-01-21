package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class BlockManager {
    public ArrayList<Block> blockList;
    Context context;
    public BlockManager(Context context){
        this.context = context;
        this.blockList = new ArrayList<>();
    }

    public void createBlocks(int level){
        if(level==1){
            blockList.add(new Block(context, 0,800,1));
            blockList.add(new Block(context, 600,200,1));
            blockList.add(new Block(context, 1200,800,1));
            blockList.add(new Block(context, 0,1800,1));
            blockList.add(new Block(context, 1200,1400,1));
        }
    }

    //Funkcja rysująca wszystkie bloki z blockList
    public void drawBlocks(Canvas canvas, GameDisplay gameDisplay){
        for(Block blck : blockList){
            blck.draw(canvas, gameDisplay);
        }
    }

    //Metoda sprawdzająca czy punkt x, y znajduje się na jakimkolwiek bloku z blocklist
    public boolean checkIfOnBlock(double x, double y){
        for(Block blks : blockList){
            if(blks.checkIfHit(x, y)){
                return true;
            }
        }
        return false;
    }
}
