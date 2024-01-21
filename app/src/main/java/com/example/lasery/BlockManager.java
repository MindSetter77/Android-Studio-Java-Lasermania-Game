package com.example.lasery;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class BlockManager {
    Block block;
    public ArrayList<Block> blockList;
    Context context;
    public BlockManager(Context context){
        this.context = context;
        this.blockList = new ArrayList<>();
    }

    public void createBlocks(int level){
        if(level==1){

            blockList.add(new Block(context, 0,800,1,false));

            blockList.add(new Block(context, 600,200,1,false));

            blockList.add(new Block(context, 1200,800,1,false));

            blockList.add(new Block(context, 300, 500, 2, true));

            blockList.add(new Block(context, 900, 500, 2, true));
        }
    }

    public void drawBlocks(Canvas canvas, GameDisplay gameDisplay){
        for(Block blck : blockList){
            blck.draw(canvas, gameDisplay);
        }
    }

    public boolean checkIfOnBlock(double x, double y){
        for(Block blks : blockList){
            if(blks.checkIfHit(x, y) && !blks.isMovable()){
                return true;
            }
        }
        return false;
    }
}
