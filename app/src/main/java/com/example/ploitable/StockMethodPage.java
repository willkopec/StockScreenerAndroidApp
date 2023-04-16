package com.example.ploitable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockMethodPage extends AppCompatActivity {
    public static String usersStockList;
    public static String currentIndex;
    public static String timeFrame;
    public static String strategy;

    private Button deathCrossButton;
    private Button goldCrossButton;
    private Button cancelScanButton;
    private Button viewStocksButton;

    private scanGoldCrossAndChangeText s2;
    private scanDeathCrossAndChangeText s1;

    private PyObject myScript;
    private Python py;

    private boolean cancelledScan = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_method_page);

        viewStocksButton = (Button) findViewById(R.id.viewStocksBtn);
        viewStocksButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setListViewPage();
            }
        });
        viewStocksButton.setVisibility(View.GONE);

        cancelScanButton = (Button) findViewById(R.id.cancelScan);
        cancelScanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cancelScanButton.setVisibility(View.GONE);
                cancelledScan = true;
                py.getModule("test").callAttr("stop_loop");
            }
        });

        cancelScanButton.setVisibility(View.GONE);

        deathCrossButton = (Button) findViewById(R.id.deathCrossButton);
        deathCrossButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewStocksButton.setVisibility(View.GONE);
                s1 = new scanDeathCrossAndChangeText();
                cancelScanButton.setVisibility(View.VISIBLE);
                s1.execute();
            }
        });

        goldCrossButton = (Button) findViewById(R.id.goldCrossButton);
        goldCrossButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewStocksButton.setVisibility(View.GONE);
                s2 = new scanGoldCrossAndChangeText();
                cancelScanButton.setVisibility(View.VISIBLE);
                s2.execute();
            }
        });

    }

    public void setUnclickableButtons(){
        goldCrossButton.setEnabled(false);
        deathCrossButton.setEnabled(false);
    }

    public void setClickableButtons(){
        goldCrossButton.setEnabled(true);
        deathCrossButton.setEnabled(true);
    }

    public void setDeathCrossMethod(){
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }


        strategy = "down";
        py = Python.getInstance();
        myScript = py.getModule("test");
        usersStockList = myScript.callAttr( "checkStocks",currentIndex, timeFrame, strategy, "", "", 5000).toString();
    }

    public void setGoldCrossMethod(){
        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        strategy = "up";
        py = Python.getInstance();
        myScript = py.getModule("test");

        usersStockList = myScript.callAttr( "checkStocks",currentIndex, timeFrame, strategy, "", "", 5000).toString();
    }

    public void setListViewPage(){
        Intent intent = new Intent(this, StockListViewPage.class);
        startActivity(intent);
    }

    public void changeLoadingText(){
        TextView scanningText = (TextView)findViewById(R.id.scanningText);
        scanningText.setText("Loading... Please wait!");
    }

    public void finishedText(){
        TextView scanningText = (TextView)findViewById(R.id.scanningText);
        scanningText.setText("");
    }

    private class scanGoldCrossAndChangeText extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void...voids){
            setGoldCrossMethod();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setUnclickableButtons();
            changeLoadingText();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            finishedText();
            setClickableButtons();
            cancelScanButton.setVisibility(View.GONE);
            viewStocksButton.setVisibility(View.VISIBLE);
            if(cancelledScan){
                usersStockList = "";
                cancelledScan = false;
                viewStocksButton.setVisibility(View.GONE);
            } else {
                setListViewPage();
            }
        }
    }

    private class scanDeathCrossAndChangeText extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void...voids){
            setDeathCrossMethod();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setUnclickableButtons();
            changeLoadingText();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            finishedText();
            setClickableButtons();
            cancelScanButton.setVisibility(View.GONE);
            viewStocksButton.setVisibility(View.VISIBLE);

            if(cancelledScan){
                usersStockList = "";
                cancelledScan = false;
                viewStocksButton.setVisibility(View.GONE);
            } else {
                setListViewPage();
            }
        }
    }

}

