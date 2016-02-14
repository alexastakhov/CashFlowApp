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
public class RecordEditDialog extends DialogFragment {

    private int initHeight;
    private int initWidth = 350;
    private long recordId;
    private View view;

    private static final String TAG = "RecordEditDialog";

    public static RecordEditDialog getInstance(ChargeRecord item) {
        RecordEditDialog dialog = new RecordEditDialog();
        Bundle args = new Bundle();

        args.putLong("date", item.getDate().getTime());
        args.putFloat("amount", item.getAmount());
        args.putLong("id", item.getId());
        args.putString("comment", item.getComment());
        args.putInt("category", item.getCategory().getId());
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        categoryName = getArguments().getString("name");
//        priority = getArguments().getInt("pri");
//        categoryId = getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_edit_dialog, container, false);
//        Button saveBtn = (Button)view.findViewById(R.id.recordSaveButton);
//        Button cancelBtn = (Button)view.findViewById(R.id.recordCancelButton);
//        Button deleteBtn = (Button)view.findViewById(R.id.recordDeleteButton);
//
//        getDialog().setTitle(R.string.category_dialog_header);
//
//        initHeight = getDialog().getWindow().getAttributes().height;
//        getDialog().getWindow().setLayout(initWidth, initHeight);
//
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = editText.getText().toString();
//
//                if (editText.getText().length() == 0) {
//                    showErrorText(R.string.category_dialog_error_name_text);
//                }
//                else if (!categoryName.equals(text) && isCategoryExists(text)) {
//                    showErrorText(R.string.category_dialog_error_exists_text);
//                }
//                else {
//                    saveCategory(categoryId, text, (checkBox.isChecked() ? 1 : 0));
//                    closeDialog();
//                }
//            }
//        });
//
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeDialog();
//            }
//        });
//
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteRecord(recordId);
//                closeDialog();
//            }
//        });

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }

//    private void saveCategory(int id, String newName, int prio) {
//        ((CategoryActivity)getActivity()).changeCategory(id, newName, prio);
//    }
//
//    private void deleteRecord(long id) {
//        ((CategoryActivity)getActivity()).deleteCategory(categoryName);
//    }
//
//    private boolean isCategoryExists(String name) {
//        return ((CategoryActivity)getActivity()).isCategoryNameExists(name);
//    }
}
