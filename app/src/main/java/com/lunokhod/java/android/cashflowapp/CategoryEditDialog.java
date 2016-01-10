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
public class CategoryEditDialog extends DialogFragment {
    String category;
    boolean priority;

    public static CategoryEditDialog getInstance(String category, boolean prio) {
        CategoryEditDialog dialog = new CategoryEditDialog();
        Bundle args = new Bundle();

        args.putString("cat", category);
        args.putBoolean("pri", prio);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        category = getArguments().getString("cat");
        priority = getArguments().getBoolean("pri");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.category_edit_dialog, container, false);
        EditText editText = (EditText)view.findViewById(R.id.categoryEditText);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.categoryPrioCheckBox);
        Button saveBtn = (Button)view.findViewById(R.id.categorySaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.categoryCancelButton);
        Button deleteBtn = (Button)view.findViewById(R.id.categoryDeleteButton);

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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        editText.setText(category);
        checkBox.setChecked(priority);

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }
}
