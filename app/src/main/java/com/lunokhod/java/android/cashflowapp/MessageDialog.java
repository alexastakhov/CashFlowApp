package com.lunokhod.java.android.cashflowapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alex on 11.01.2016.
 */
public class MessageDialog extends DialogFragment {

    public static MessageDialog newInstance(String title, String message) {
        MessageDialog dialog = new MessageDialog();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putString("msg", message);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("title");
        String message = getArguments().getString("msg");

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                close();
            }
        });

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void close() {
        this.dismiss();
    }
}
