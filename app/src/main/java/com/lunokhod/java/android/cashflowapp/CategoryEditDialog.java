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

public class CategoryEditDialog extends DialogFragment {

    private int initHeight;
    private int initWidth = 350;
    private String categoryName;
    private int priority;
    private int categoryId;
    private View view;
    private TextView errorText;
    private EditText editText;
    private CheckBox checkBox;

    private static final String TAG = "CategoryEditDialog";

    public static CategoryEditDialog getInstance(CategoryItem item) {
        CategoryEditDialog dialog = new CategoryEditDialog();
        Bundle args = new Bundle();

        args.putString("name", item.getName());
        args.putInt("pri", item.getPriority());
        args.putInt("id", item.getId());
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryName = getArguments().getString("name");
        priority = getArguments().getInt("pri");
        categoryId = getArguments().getInt("id");
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

        initHeight = getDialog().getWindow().getAttributes().height;
        getDialog().getWindow().setLayout(initWidth, initHeight);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if (editText.getText().length() == 0) {
                    showErrorText(R.string.category_dialog_error_name_text);
                }
                else if (!categoryName.equals(text) && isCategoryExists(text)) {
                    showErrorText(R.string.category_dialog_error_exists_text);
                }
                else {
                    saveCategory(categoryId, text, (checkBox.isChecked() ? 1 : 0));
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
                if (DataManager.getInstance(null).getRecordsNumWithCategory(categoryId) > 0)
                    showErrorText(R.string.category_dialog_category_in_use);
                else {
                    deleteCategory(categoryId);
                    closeDialog();
                }
            }
        });

        editText.setText(categoryName);
        checkBox.setChecked(priority == 1);

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }

    private void saveCategory(int id, String newName, int priority) {
        ((CategoryActivity)getActivity()).changeCategory(id, newName, priority);
    }

    private void deleteCategory(int categoryId) {
        ((CategoryActivity)getActivity()).deleteCategory(categoryId);
    }

    private boolean isCategoryExists(String name) {
        return ((CategoryActivity)getActivity()).isCategoryNameExists(name);
    }

    private void showErrorText(int resId) {
        errorText.setText(resId);
        errorText.setVisibility(View.VISIBLE);
    }
}
