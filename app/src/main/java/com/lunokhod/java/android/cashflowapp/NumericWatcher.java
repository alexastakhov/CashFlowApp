package com.lunokhod.java.android.cashflowapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumericWatcher implements TextWatcher {

    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;
    private EditText editText;

    public NumericWatcher(EditText editText)
    {
        df = new DecimalFormat("#,###.##");
        df.setMaximumFractionDigits(2);
        df.setMaximumIntegerDigits(7);
        df.setDecimalSeparatorAlwaysShown(true);

        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        dfnd = new DecimalFormat("#,###");
        this.editText = editText;
        hasFractionalPart = true;
    }

    @SuppressWarnings("unused")
    private static final String TAG = "NumericWatcher";

    @Override
    public void afterTextChanged(Editable s)
    {
        Log.i(TAG, "afterTextChanged; s == " + s.toString());
        editText.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = editText.getText().length();
            String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            String sp = String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator());

            if (v.contains(sp)) {
                Log.i(TAG, "v.contains(sp); v == " + v);

                if (v.length() - v.lastIndexOf(sp) == 4) {
                    v = v.substring(0, v.length() - 1);
                    Log.i(TAG, "v.substring; v == " + v);
                }
            }

            Log.i(TAG, editText.getText().toString());

            Number num = df.parse(v);
            int selectionStart = editText.getSelectionStart();

            Log.i(TAG, "num == " + num.toString());

            if (hasFractionalPart) {
                editText.setText(df.format(num));
            }
            else {
                editText.setText(dfnd.format(num));
            }

            endlen = editText.getText().length();
            int sel = (selectionStart + (endlen - inilen));

            if (sel > 0 && sel <= editText.getText().length()) {
                editText.setSelection(sel);
            }
            else {
                // place cursor at the end?
                editText.setSelection(editText.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            Log.i(TAG, "NumberFormatException");
        } catch (ParseException e) {
            Log.i(TAG, "ParseException");
        }

        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        Log.i(TAG, "beforeTextChanged; s == " + s.toString());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        Log.i(TAG, "onTextChanged");
        Log.i(TAG, "getDecimalSeparator() == " + String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
        {
            hasFractionalPart = true;
            Log.i(TAG, "hasFractionalPart = true; s == " + s.toString());
        }
        else {
            hasFractionalPart = false;
            Log.i(TAG, "hasFractionalPart = false; s == " + s.toString());
        }
    }
}