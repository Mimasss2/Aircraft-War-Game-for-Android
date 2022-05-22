package com.example.aircraft;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static int height;
    public static int width;
    public static boolean music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;
        width = dm.widthPixels;
        music = true;

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.GameActivity"));
                startActivity(i);
            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.GameActivity"));
                startActivity(i);
            }
        });

        Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.GameActivity"));
                startActivity(i);
            }
        });
    }
}