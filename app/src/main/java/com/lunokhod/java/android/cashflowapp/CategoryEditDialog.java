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
public class CategoryEditDialog extends DialogFragment {

    private int initHeigth;
    private int initWidth = 350;
    private String categoryName;
    private int priority;
    private View view;
    private TextView errorText;
    private EditText editText;
    private CheckBox checkBox;

    private static final String TAG = "CategoryEditDialog";

    public static CategoryEditDialog getInstance(String category, int prio) {
        CategoryEditDialog dialog = new CategoryEditDialog();
        Bundle args = new Bundle();

        args.putString("cat", category);
        args.putInt("pri", prio);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryName = getArguments().getString("cat");
        priority = getArguments().getInt("pri");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category_edit_dialog, container, false);
        errorText = (TextView)view.findViewById(R.id.errorTextView);
        editText = (EditText)view.findViewById(R.id.categoryEditText);
        checkBox = (CheckBox)view.findViewById(R.id.categoryPrioCheckBox);
        Button saveBtn = (Button)view.findViewById(R.id.categorySaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.categoryCancelButton);
        Button deleteBtn = (Button)view.findViewById(R.id.categoryDeleteButton);

        getDialog().setTitle(R.string.category_dialog_header);

        initHeigth = getDialog().getWindow().getAttributes().height;
        getDialog().getWindow().setLayout(initWidth, initHeigth);



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if (editText.getText().length() == 0) {
                    showNameErrorText();
                }
                else if (!categoryName.equals(text) && isCategoryExists(text)) {
                    showCategoryExistsErrorText();
                }
                else {
                    saveCategory(categoryName, text, (checkBox.isChecked() ? 1 : 0));
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(categoryName);
                closeDialog();
            }
        });

        editText.setText(categoryName);
        checkBox.setChecked(priority == 1 ? true : false);

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }

    private void saveCategory(String oldName, String newName, int prio) {
        ((CategoryActivity)getActivity()).changeCategory(oldName, newName, prio);
    }

    private void deleteCategory(String categoryName) {
        ((CategoryActivity)getActivity()).deleteCategory(categoryName);
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

    private void hideErrorText() {
        errorText.setVisibility(View.GONE);
    }
}
