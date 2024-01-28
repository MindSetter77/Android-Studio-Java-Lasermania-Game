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
            //Y = 400
            // Lewa ściana
            blockList.add(new Block(context, 0, 0, 1, false));
            blockList.add(new Block(context, 0, 200, 1, false));
//

// Prawa ściana
            blockList.add(new Block(context, 1200, 0, 1, false));
            blockList.add(new Block(context, 1200, 200, 1, false));
            blockList.add(new Block(context, 1200, 400, 1, false));
// ... możesz kontynuować dodawanie bloków w dół

// Górna ściana
            blockList.add(new Block(context, 200, 0, 1, false));
            blockList.add(new Block(context, 400, 0, 1, false));
            blockList.add(new Block(context, 600, 0, 1, false));
            blockList.add(new Block(context, 800, 0, 1, false));
            blockList.add(new Block(context, 1000, 0, 1, false));

// Dolna ściana
            blockList.add(new Block(context, 200, 1800, 1, false));
            blockList.add(new Block(context, 400, 1800, 1, false));
            blockList.add(new Block(context, 600, 1800, 1, false));
            blockList.add(new Block(context, 800, 1800, 1, false));
            blockList.add(new Block(context, 1000, 1800, 1, false));

// Wewnętrzne przeszkody

            blockList.add(new Block(context, 200, 800, 1, true));
            blockList.add(new Block(context, 600, 800, 1, true));

            blockList.add(new Block(context, 1000, 600, 1, false));
            blockList.add(new Block(context, 1000, 800, 1, false));
            blockList.add(new Block(context, 1000, 1000, 1, false));
            blockList.add(new Block(context, 1000, 200, 1, true));

// Tworzenie korytarzy

            blockList.add(new Block(context, 1000, 1200, 1, false));


// Pojedyncze bloki
            blockList.add(new Block(context, 600, 200, 1, false));
            blockList.add(new Block(context, 600, 1600, 1, false));

            blockList.add(new Block(context, 800, 1000, 1, false));

            blockList.add(new Block(context, 400, 1400, 1, false));
            blockList.add(new Block(context, 800, 600, 1, false));
            /////////

            // Rozbudowa pozioma w prawą stronę
            blockList.add(new Block(context, 1400, 200, 1, false));
            blockList.add(new Block(context, 1600, 400, 1, false));
            blockList.add(new Block(context, 1800, 200, 1, false));
            blockList.add(new Block(context, 2000, 400, 1, false));
            blockList.add(new Block(context, 2200, 200, 1, false));
            blockList.add(new Block(context, 2400, 400, 1, false));
            blockList.add(new Block(context, 2600, 200, 1, false));
            blockList.add(new Block(context, 2800, 400, 1, false));
            blockList.add(new Block(context, 3000, 200, 1, false));
            blockList.add(new Block(context, 3200, 400, 1, false));
            blockList.add(new Block(context, 3400, 200, 1, false));
            blockList.add(new Block(context, 3600, 400, 1, false));
            blockList.add(new Block(context, 3800, 200, 1, false));
            blockList.add(new Block(context, 4000, 400, 1, false));

            blockList.add(new Block(context, 2200, 1500, 1, true));

// Rozbudowa dolnej ściany
            blockList.add(new Block(context, 1800, 1800, 1, false));
            blockList.add(new Block(context, 2000, 1800, 1, false));
            blockList.add(new Block(context, 2200, 1800, 1, false));
            blockList.add(new Block(context, 2400, 1800, 1, false));
            blockList.add(new Block(context, 2600, 1800, 1, false));
            blockList.add(new Block(context, 2800, 1800, 1, false));
            blockList.add(new Block(context, 3000, 1800, 1, false));
            blockList.add(new Block(context, 3200, 1800, 1, false));
            blockList.add(new Block(context, 3400, 1800, 1, false));
            blockList.add(new Block(context, 3600, 1800, 1, false));
            blockList.add(new Block(context, 3800, 1800, 1, false));
            blockList.add(new Block(context, 4000, 1800, 1, false));

        }
    }

    public void drawBlocks(Canvas canvas, GameDisplay gameDisplay){
        for(Block blck : blockList){
            blck.draw(canvas, gameDisplay);
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
