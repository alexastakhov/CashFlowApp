package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

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
    public ChargeRecord getItem(int position) {
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
        if (item.getCredit() == ChargeRecord.CREDIT) {
            amountTextView.setText("+" + formatAmount(item.getAmount()));
            amountTextView.setTextColor(view.getResources().getColor(R.color.recordRowCreditAmountColor));
        }
        else
            amountTextView.setText(formatAmount(item.getAmount()));

        categoryTextView.setText(item.getCategory().getName());
        dayTextView.setText(String.valueOf(date.get(Calendar.DATE)));
        monthTextView.setText(monthToString(date.get(Calendar.MONTH)));
        timeTextView.setText(formatTime(date));

        return view;
    }

    public void setDataObjects(ChargeRecord[] objects) {
        records = objects;
        notifyDataSetChanged();
    }

    private String monthToString(int month) {
        switch (month) {
            case Calendar.JANUARY : return appContext.getResources().getString(R.string.month_jan);
            case Calendar.FEBRUARY : return appContext.getResources().getString(R.string.month_feb);
            case Calendar.MARCH : return appContext.getResources().getString(R.string.month_mar);
            case Calendar.APRIL : return appContext.getResources().getString(R.string.month_apr);
            case Calendar.MAY : return appContext.getResources().getString(R.string.month_may);
            case Calendar.JUNE : return appContext.getResources().getString(R.string.month_jun);
            case Calendar.JULY : return appContext.getResources().getString(R.string.month_jul);
            case Calendar.AUGUST : return appContext.getResources().getString(R.string.month_aug);
            case Calendar.SEPTEMBER : return appContext.getResources().getString(R.string.month_sep);
            case Calendar.OCTOBER : return appContext.getResources().getString(R.string.month_oct);
            case Calendar.NOVEMBER : return appContext.getResources().getString(R.string.month_nov);
            case Calendar.DECEMBER : return appContext.getResources().getString(R.string.month_dec);
            default : return "";
        }
    }

    private String formatAmount(float num) {
        String[] parts = String.format("%.2f", num).split(",");
        String str = "";
        int cnt = 1;

        for (int i = parts[0].length() - 1; i >= 0; i--) {
            str = parts[0].charAt(i) + str;
            if (cnt == 3 && i > 0) {
                str = " " + str;
                cnt = 0;
            }
            cnt++;
        }
        str = str + "," + parts[1];

        return str;
    }

    private String formatTime(Calendar date) {
        String h = String.format("%02d", date.get(Calendar.HOUR_OF_DAY));
        String d = String.format("%02d", date.get(Calendar.MINUTE));

        return (h + ":" + d);
    }
}
