package com.lunokhod.java.android.cashflowapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by alex on 10.01.2016.
 */
public class CategoryNewDialog extends DialogFragment {

    private int initHeigh;
    private int initWidth = 350;

    public static CategoryNewDialog getInstance() {
        CategoryNewDialog dialog = new CategoryNewDialog();

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.category_new_dialog, container, false);

        getDialog().setTitle(R.string.category_dialog_header2);
        initHeigh = getDialog().getWindow().getAttributes().height;
        getDialog().getWindow().setLayout(initWidth, initHeigh);

        final EditText editText = (EditText)view.findViewById(R.id.categoryEditText);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.categoryPrioCheckBox);
        Button saveBtn = (Button)view.findViewById(R.id.categorySaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.categoryCancelButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0)
                    showNameErrorText(view, true);
                else {
                    showNameErrorText(view, false);
                    closeDialog();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }

    private void saveCategory(String oldName, String newName, boolean prio) {

    }

    private void deleteCategory(String categoryName) {
        ((CategoryActivity)getActivity()).deleteCategory(categoryName);
    }

    private void showNameErrorText(View view, boolean show) {
        TextView errorText = (TextView)view.findViewById(R.id.errorTextView);
        //int tvHeihgt = errorText.getHeight();
        int mHeight = ((LinearLayout.LayoutParams)errorText.getLayoutParams()).topMargin;
        int tvHeihgt = ((LinearLayout.LayoutParams)errorText.getLayoutParams()).height;

        errorText.setText(R.string.category_dialog_error_name_text);
        if (show) {
            getDialog().getWindow().setLayout(initWidth, initHeigh + tvHeihgt + mHeight);
            errorText.setVisibility(View.VISIBLE);
        }
        else {
            getDialog().getWindow().setLayout(initWidth, initHeigh);
            errorText.setVisibility(View.GONE);
        }
    }
}
