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

    private LayoutInflater inflater;
    private Context appContext;
    private DataManager dataManager;
    private CategoryItem[] categories;

    public CategoryListViewAdapter(Context context, DataManager dataManager) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appContext = context;
        this.dataManager = dataManager;

        if (dataManager != null)
            categories = dataManager.getCategories();
        else
            categories = null;
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
            view = inflater.inflate(R.layout.category_list_row, null);
        }

        TextView text = (TextView)view.findViewById(R.id.categoryTextView);
        ImageView image = (ImageView)view.findViewById(R.id.categoryPrioImageView);

        text.setText(item.getName());
        if (item.getPriority())
            image.setImageResource(R.mipmap.ic_star_rate_black_18dp);
        else
            image.setImageResource(R.drawable.empty_icon);

        return view;
    }

    public void refreshData() {
        categories = dataManager.getCategories();
        this.notifyDataSetChanged();
    }
}
