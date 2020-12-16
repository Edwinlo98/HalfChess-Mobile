package com.example.proyekaihalfchess;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    CanvasActivity drawView;
    Button btnNew, btnLoad, btnExit;
//    HalfChess halfchess = new HalfChess();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNew=findViewById(R.id.btnNew);
        btnLoad=findViewById(R.id.btnLoad);
        btnExit=findViewById(R.id.btnExit);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(),NextActivity.class);
                //Intent i = new Intent(getBaseContext(), ModeActivity.class);
                Intent i = new Intent(getApplicationContext(), ModeActivity.class);
                startActivity(i);
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),btnLoad.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
//        drawView = new CanvasActivity(this);
//        drawView.setBackgroundColor(Color.WHITE);
//        setContentView(drawView);
    }

}

//class CanvasActivity extends View {
//    Paint paint = new Paint();
//    private static final int width = 4, height=8;
//
//    public CanvasActivity(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(3);
//        canvas.drawRect(130, 130, 180, 180, paint);
//        paint.setStrokeWidth(0);
//        paint.setColor(Color.CYAN);
//        canvas.drawRect(133, 160, 177, 177, paint );
//        paint.setColor(Color.YELLOW);
//        canvas.drawRect(133, 133, 177, 160, paint );
//        for (int i=0;i<)
//    }
//}
