package com.example.ploitable;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button spButton;
    private Button dowButton;
    private Button nsdqButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spButton = (Button) findViewById(R.id.spButton);
        spButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setToSP();
            }
        });

        nsdqButton = (Button) findViewById(R.id.nasdaqButton);
        nsdqButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setToNasdaq();
            }
        });

        dowButton = (Button) findViewById(R.id.dowButton);
        dowButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setToDow();
            }
        });


    }

    private void setToSP(){
        System.out.println("SET TO S AND P 500");
        StockMethodPage.currentIndex = "sp";
        Intent intent = new Intent(this, timeFramePage.class);
        startActivity(intent);
    }

    private void setToDow(){
        StockMethodPage.currentIndex = "dow";
        Intent intent = new Intent(this, timeFramePage.class);
        startActivity(intent);
    }

    private void setToNasdaq(){
        StockMethodPage.currentIndex = "nsdq";
        Intent intent = new Intent(this, timeFramePage.class);
        startActivity(intent);
    }

}