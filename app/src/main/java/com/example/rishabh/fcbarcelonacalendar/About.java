package com.example.rishabh.fcbarcelonacalendar;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView i = (ImageView) findViewById(R.id.imageView2);
        i.setImageResource(R.drawable.rk);
    }
}
