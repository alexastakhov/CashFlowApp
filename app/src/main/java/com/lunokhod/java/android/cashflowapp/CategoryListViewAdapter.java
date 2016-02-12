package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alex on 08.01.2016.
 */
public class CategoryListViewAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Context appContext;
    private CategoryItem[] categories;

    @SuppressWarnings("unused")
    private static final String TAG = "CategoryListViewAdapter";

    public CategoryListViewAdapter(Context context, CategoryItem[] objects) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appContext = context;
        categories = objects;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public CategoryItem getItem(int position) {
        return categories[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CategoryItem item = categories[position];

        if (convertView == null) {
            view = inflater.inflate(R.layout.category_list_simple_row, null);
        }

        TextView text = (TextView)view.findViewById(R.id.categoryTextView);

        text.setText(item.getName());
        return view;
    }

    public void setDataObjects(CategoryItem[] objects) {
        categories = objects;
        notifyDataSetChanged();
    }
}
