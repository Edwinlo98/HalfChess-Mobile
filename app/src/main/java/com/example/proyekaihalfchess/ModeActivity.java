package com.example.proyekaihalfchess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModeActivity extends AppCompatActivity {
    CanvasActivity drawView;
    Button btnPvP, btnPvAE, btnPvAN, btnPvAH;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_game);
        btnPvP=findViewById(R.id.btnPP);
        btnPvAE=findViewById(R.id.btnPAE);
        btnPvAN=findViewById(R.id.btnPAN);
        btnPvAH=findViewById(R.id.btnPAH);
        btnPvP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.board_game);
                DrawBoard();
            }
        });

    }

    private void DrawBoard() {
        drawView = new CanvasActivity(this);
        setContentView(drawView);
    }

}
