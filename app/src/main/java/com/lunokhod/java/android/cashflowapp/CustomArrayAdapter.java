package com.lunokhod.java.android.cashflowapp;

/**
 * Created by Alexey Astakhov on 05.01.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter {
    private final Context context;
    private final int resource;
    private int textViewResourceId;
    private boolean selected = false;
    private boolean nextSwitched = false;
    private int selectedColor;

    @SuppressWarnings("unused")
    private static final String TAG = "CustomArrayAdapter";

    public CustomArrayAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        selectedColor = Color.BLACK;
    }

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        selectedColor = Color.BLACK;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView()");

        if (convertView == null)
            convertView = View.inflate(context, resource, null);

        if (selected) {
            TextView textView = (TextView) convertView;
            textView.setText((String)getItem(position));
            textView.setTextColor(selectedColor);
        }

        if (nextSwitched)
            selected = true;

        return convertView;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setNextSwitched() {
        nextSwitched = true;
    }

    public void setSelectedColor(int color) {
        selectedColor = color;
    }
}

