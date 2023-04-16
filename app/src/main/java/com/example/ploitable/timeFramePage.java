package com.example.ploitable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class timeFramePage extends AppCompatActivity {

    Button dayButton;
    Button weekButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_frame_page);

        dayButton = (Button) findViewById(R.id.dayButton);
        dayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setToDaily();
            }
        });

        weekButton = (Button) findViewById(R.id.weekButton);
        weekButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setToWeekly();
            }
        });
    }

    public void setToDaily(){
        StockMethodPage.timeFrame = "d";
        Intent intent = new Intent(this, StockMethodPage.class);
        startActivity(intent);
    }

    public void setToWeekly(){
        StockMethodPage.timeFrame = "wk";
        Intent intent = new Intent(this, StockMethodPage.class);
        startActivity(intent);
    }
}