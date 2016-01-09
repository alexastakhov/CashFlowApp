package com.lunokhod.java.android.cashflowapp;

/**
 * Created by Alexey Astakhov on 05.01.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private int textViewResourceId;
    private String[] objects;
    private boolean selected = false;
    private int selectedColor;

    @SuppressWarnings("unused")
    private static final String TAG = "CustomArrayAdapter";

    public CustomArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        selectedColor = Color.BLACK;
    }

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
        selectedColor = Color.BLACK;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, resource, null);

        if (selected) {
            TextView textView = (TextView) convertView;
            textView.setText(objects[position]);
            textView.setTextColor(selectedColor);
        }
        return convertView;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSelectedColor(int color) {
        selectedColor = color;
    }
}

