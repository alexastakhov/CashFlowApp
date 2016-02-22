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
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by alex on 10.01.2016.
 */
public class RecordEditDialog extends DialogFragment {

    private int initHeight;
    private int initWidth = 350;
    private View view;
    private long date;
    private float amount;
    private long recordId;
    private String comment;
    private int categoryId;
    private int credit;


    private static final String TAG = "RecordEditDialog";

    public static RecordEditDialog getInstance(ChargeRecord item) {
        RecordEditDialog dialog = new RecordEditDialog();
        Bundle args = new Bundle();

        args.putLong("date", item.getDate().getTime());
        args.putFloat("amount", item.getAmount());
        args.putLong("id", item.getId());
        args.putString("comment", item.getComment());
        args.putInt("category", item.getCategory().getId());
        args.putInt("credit", item.getCredit());
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        date = getArguments().getLong("date");
        amount = getArguments().getFloat("amount");
        recordId = getArguments().getLong("id");
        comment = getArguments().getString("comment");
        categoryId = getArguments().getInt("category");
        credit = getArguments().getInt("credit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_edit_dialog, container, false);
        Button saveBtn = (Button)view.findViewById(R.id.recordSaveButton);
        Button cancelBtn = (Button)view.findViewById(R.id.recordCancelButton);
        Button deleteBtn = (Button)view.findViewById(R.id.recordDeleteButton);
        EditText recordAmountEditText = (EditText)view.findViewById(R.id.recordAmountEditText);
        EditText recordDateEditText = (EditText)view.findViewById(R.id.recordDateEditText);
        Spinner recordCategorySpinner = (Spinner)view.findViewById(R.id.recordCategorySpinner);
        EditText recordCommentEditText = (EditText)view.findViewById(R.id.recordCommentEditText);

        getDialog().setTitle(R.string.record_dialog_header);

        initHeight = getDialog().getWindow().getAttributes().height;
        getDialog().getWindow().setLayout(initWidth, initHeight);
        recordAmountEditText.addTextChangedListener(new NumericWatcher(recordAmountEditText));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    closeDialog();
//                }
            }
        });
//
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord(recordId);
                closeDialog();
            }
        });

        recordAmountEditText.setText(formatAmount(amount, credit));
        recordCommentEditText.setText(comment);

        return view;
    }

    private void closeDialog() {
        this.dismiss();
    }

    private void saveRecord(int id, String newName, int prio) {
        ((RecordsActivity)getActivity()).changeRecord(id, newName, prio);
    }

    private void deleteRecord(long recordId) {
        ((RecordsActivity)getActivity()).deleteRecord(recordId);
    }

    private String formatAmount(float num, int cr) {
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

        if (!parts[1].equals("00"))
            str = str + "." + parts[1];

        if (cr == ChargeRecord.CREDIT)
            str = "+" + str;

        return str;
    }
}
