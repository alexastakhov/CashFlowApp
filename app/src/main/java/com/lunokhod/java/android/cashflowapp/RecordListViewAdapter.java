package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by alex on 09.02.2016.
 */
public class RecordListViewAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Context appContext;
    private ChargeRecord[] records;

    private static final String TAG = "RecordListViewAdapter";

    public RecordListViewAdapter(Context context, ChargeRecord[] objects) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appContext = context;
        records = objects;
    }

    @Override
    public int getCount() {
        return records.length;
    }

    @Override
    public Object getItem(int position) {
        return records[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ChargeRecord item = records[position];
        Calendar date = Calendar.getInstance();

        if (convertView == null) {
            view = inflater.inflate(R.layout.record_list_simple_row, null);
        }

        TextView amountTextView = (TextView)view.findViewById(R.id.amountTextView);
        TextView categoryTextView = (TextView)view.findViewById(R.id.categoryTextView);
        TextView dayTextView = (TextView)view.findViewById(R.id.dayTextView);
        TextView monthTextView = (TextView)view.findViewById(R.id.monthTextView);
        TextView timeTextView = (TextView)view.findViewById(R.id.timeTextView);

        date.setTime(item.getDate());
        amountTextView.setText(String.valueOf(item.getAmount()));
        categoryTextView.setText(item.getCategory().getName());
        dayTextView.setText(String.valueOf(date.get(Calendar.DATE)));
        monthTextView.setText(monthToString(view, date.get(Calendar.MONTH)));
        timeTextView.setText(date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE));

        return view;
    }

    public void setDataObjects(ChargeRecord[] objects) {
        records = objects;
        notifyDataSetChanged();
    }

    private String monthToString(View view, int month) {
        switch (month) {
            case 1 : return appContext.getResources().getString(R.string.month_jan);
            case 2 : return appContext.getResources().getString(R.string.month_feb);
            case 3 : return appContext.getResources().getString(R.string.month_mar);
            case 4 : return appContext.getResources().getString(R.string.month_apr);
            case 5 : return appContext.getResources().getString(R.string.month_may);
            case 6 : return appContext.getResources().getString(R.string.month_jun);
            case 7 : return appContext.getResources().getString(R.string.month_jul);
            case 8 : return appContext.getResources().getString(R.string.month_aug);
            case 9 : return appContext.getResources().getString(R.string.month_sep);
            case 10 : return appContext.getResources().getString(R.string.month_oct);
            case 11 : return appContext.getResources().getString(R.string.month_nov);
            case 12 : return appContext.getResources().getString(R.string.month_dec);
            default : return "";
        }
    }
}
