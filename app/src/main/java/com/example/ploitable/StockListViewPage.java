package com.example.ploitable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StockListViewPage extends AppCompatActivity {
    public String stockList[];
    public String linkStart;
    public String fullLink;
    ArrayList<Stock> theStockList = new ArrayList<>();

    ListView simpleList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list_view_page);

        if(!StockMethodPage.usersStockList.equals("")){
            stockList = StockMethodPage.usersStockList.split(",");

            for(int i = 0; i < stockList.length; i++){
                linkStart = "https://finance.yahoo.com/quote/";
                fullLink = linkStart + stockList[i];
                Stock currentStock = new Stock(stockList[i], fullLink);
                theStockList.add(currentStock);
            }

        } else {
            Stock emptyListPlaceholderStock = new Stock("No stocks found!", "Please use a new scan method or Market Index!");
            theStockList.add(emptyListPlaceholderStock);
        }

        // on below line we are initializing our variables.
        ListView stockListView = findViewById(R.id.stockListVieww);
        Button restartScanBtn = findViewById(R.id.scanAgainBtn);

        //System.out.println(stockList[0]);

        StockListAdapter adapter = new StockListAdapter(this, R.layout.adapter_view_layout, theStockList);
        stockListView.setAdapter(adapter);

        restartScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMainActivity();
                StockMethodPage.usersStockList = "";
                StockMethodPage.timeFrame = "";
                StockMethodPage.strategy = "";
                StockMethodPage.currentIndex = "";
            }
        });

    }

    public void setMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}