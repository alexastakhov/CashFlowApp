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
import android.widget.TextView;

/**
 * Created by alex on 10.01.2016.
 */
public class CategoryNewDialog extends DialogFragment {

    private int initHeight;
    private int initWidth = 350;
    private View view;
    private TextView errorText;
    private EditText editText;
    private CheckBox checkBox;

    private static final String TAG = "CategoryNewDialog";

    public static CategoryNewDialog getInstance() {
        return new CategoryNewDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_new_dialog, container, false);
        errorText = (TextView)view.findViewById(R.id.errorTextView);
        editText = (EditText)view.findViewById(R.id.categoryEditText);
        checkBox = (CheckBox)view.findViewById(R.id.categoryPrioCheckBox);
        Button saveBtn = (Button)view.findViewById(R.id.categorySaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.categoryCancelButton);

        getDialog().setTitle(R.string.category_dialog_header2);
        initHeight = getDialog().getWindow().getAttributes().height;
        getDialog().getWindow().setLayout(initWidth, initHeight);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 0) {
                    showNameErrorText();
                }
                else if (isCategoryExists(editText.getText().toString())) {
                    showCategoryExistsErrorText();
                }
                else {
                    saveCategory(editText.getText().toString(), (checkBox.isChecked() ? 1 : 0));
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

    private void saveCategory(String name, int prio) {
        ((CategoryActivity)getActivity()).addCategory(name, prio);
    }

    private boolean isCategoryExists(String name) {
        return ((CategoryActivity)getActivity()).isCategoryExists(name);
    }

    private void showNameErrorText() {
        errorText.setText(R.string.category_dialog_error_name_text);
        errorText.setVisibility(View.VISIBLE);
    }

    private void showCategoryExistsErrorText() {
        errorText.setText(R.string.category_dialog_error_exists_text);
        errorText.setVisibility(View.VISIBLE);
    }
}
