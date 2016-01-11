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

/**
 * Created by alex on 10.01.2016.
 */
public class CategoryNewDialog extends DialogFragment {

    public static CategoryNewDialog getInstance() {
        CategoryNewDialog dialog = new CategoryNewDialog();

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.category_new_dialog, container, false);

        getDialog().setTitle(R.string.category_dialog_header2);

        int dHeigt = getDialog().getWindow().getAttributes().height;
        int dWidth = 350;
        getDialog().getWindow().setLayout(dWidth, dHeigt);

        EditText editText = (EditText)view.findViewById(R.id.categoryEditText);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.categoryPrioCheckBox);
        Button saveBtn = (Button)view.findViewById(R.id.categorySaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.categoryCancelButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
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
}
