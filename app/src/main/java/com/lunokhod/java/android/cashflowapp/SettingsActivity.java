package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/CashFlowApp";

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings_caption);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText fileNameToSaveRecords = (EditText)findViewById(R.id.fileNameToSaveRecords);
        fileNameToSaveRecords.setText(path);

        Button dropDataBaseButton = (Button)findViewById(R.id.dropDataBaseButton);
        dropDataBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().dropDataBase();
            }
        });

        Button deleteAllRecsButton = (Button)findViewById(R.id.deleteAllRecsButton);
        deleteAllRecsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().deleteAllRecords();
            }
        });

        Button saveRecordsToFileButton = (Button)findViewById(R.id.saveRecordsToFileButton);
        saveRecordsToFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecordsToFile();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRecordsToFile() {
        String path = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/CashFlowApp";
        String fileName = DateUtils.formatDateTime(getApplicationContext(), Calendar.getInstance().getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
        File dir = new File(path + "/" + fileName);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                showMsgDlg("Error", "App Folder creation error");
                return;
            }
            Toast.makeText(this, "App Folder created!", Toast.LENGTH_SHORT);
        }

        showMsgDlg("SD card path", path);
    }

    private void showMsgDlg(String title, String msg) {
        MessageDialog.newInstance(title, msg).show(getFragmentManager(), "messageDialog");
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
