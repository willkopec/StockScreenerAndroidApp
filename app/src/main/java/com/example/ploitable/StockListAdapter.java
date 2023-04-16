package com.example.ploitable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 3/14/2017.
 */

public class StockListAdapter extends ArrayAdapter<Stock> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView link;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public StockListAdapter(Context context, int resource, ArrayList<Stock> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String link = getItem(position).getLink();

        //Create the person object with the information
        Stock person = new Stock(name, link);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView stockName = (TextView) convertView.findViewById(R.id.stockName);
        TextView stockLink = (TextView) convertView.findViewById(R.id.stockLink);

        stockName.setText(name);
        stockLink.setText(link);

        return convertView;
    }
}