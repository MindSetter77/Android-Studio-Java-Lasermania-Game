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

            blockList.add(new Block(context, 0,800,1));


            blockList.add(new Block(context, 600,200,1));


            blockList.add(new Block(context, 1200,800,1));


            blockList.add(new Block(context, 100,1794,1));


            blockList.add(new Block(context, 400,2200,1));


            blockList.add(new Block(context, 1200,1400,1));






        }
    }

    public void drawBlocks(Canvas canvas){
        for(Block blck : blockList){
            blck.draw(canvas);
        }
    }

    public boolean checkIfOnBlock(double x, double y){
        for(Block blks : blockList){
            if(blks.checkIfHit(x, y)){
                return true;
            }
        }
        return false;
    }
}
