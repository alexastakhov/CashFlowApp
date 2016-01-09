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
    private boolean plusFlag;

    @SuppressWarnings("unused")
    private static final String TAG = "NumericWatcher";

    public NumericWatcher(EditText editText) {
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
        plusFlag = false;
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        plusFlag = false;

        try {
            int inilen, endlen;
            inilen = editText.getText().length();
            String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
            String sp = String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator());

            v = v.replace("-", "");
            if (v.length() == 0) {
                editText.setText(v);
                editText.addTextChangedListener(this);
                return;
            }

            if(v.contains("+")) {
                v = v.replace("+", "");
                plusFlag = true;
            }

            if (v.contains(sp)) {
                if (v.length() - v.lastIndexOf(sp) == 4) {
                    v = v.substring(0, v.length() - 1);
                }
            }

            Number num = df.parse(v);
            int selectionStart = editText.getSelectionStart();

            if (plusFlag) v = "+";
            else v = "";

            if (hasFractionalPart) {
                editText.setText(v + df.format(num));
            }
            else {
                editText.setText(v + dfnd.format(num));
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator())))
        {
            hasFractionalPart = true;
        }
        else {
            hasFractionalPart = false;
        }
    }

    public boolean getPlusFlag() {
        return plusFlag;
    }
}