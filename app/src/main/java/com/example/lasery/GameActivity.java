package com.example.lasery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    boolean fps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            fps = getIntent().getBooleanExtra("fpsUps", false);
        } catch (Exception ex){
            fps = false;
        }

        Game game = new Game(this, fps);
        setContentView(game);
    }
}
