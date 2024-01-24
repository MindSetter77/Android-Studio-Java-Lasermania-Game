package com.example.lasery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Boolean fpsUps = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        CheckBox checkbox = findViewById(R.id.checkBox);
        Button returnButton = findViewById(R.id.returnSettings);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox.isChecked()){
                    fpsUps = true;
                }
                finish();
                backToMain();
            }
        });
    }
    private void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fpsUps", fpsUps);
        startActivity(intent);
    }
}
