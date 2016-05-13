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
                DataManager.getInstance(getApplicationContext()).dropDataBase();
            }
        });

        Button deleteAllRecsButton = (Button)findViewById(R.id.deleteAllRecsButton);
        deleteAllRecsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance(getApplicationContext()).deleteAllRecords();
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
        File dir = new File(path);
        File file = new File(path + "/" + getFileName());

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                showMsgDlg("Error", "App Folder creation error");
                return;
            }
            Toast.makeText(this, "App Folder created!", Toast.LENGTH_SHORT);
        }

        showMsgDlg("SD card path", file.getPath());
    }

    private void showMsgDlg(String title, String msg) {
        MessageDialog.newInstance(title, msg).show(getFragmentManager(), "messageDialog");
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private String getFileName() {
        Calendar date = Calendar.getInstance();

        String y = String.valueOf(date.get(Calendar.YEAR));
        String d = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        d = d.length() == 1 ? "0" + d : d;

        String h = String.valueOf(date.get(Calendar.HOUR_OF_DAY));
        h = h.length() == 1 ? "0" + h : h;

        String n = String.valueOf(date.get(Calendar.MINUTE));
        n = n.length() == 1 ? "0" + n : n;

        String s = String.valueOf(date.get(Calendar.SECOND));
        s = s.length() == 1 ? "0" + s : s;

        String m;

        switch (date.get(Calendar.MONTH)) {
            case Calendar.JANUARY   : m = "01"; break;
            case Calendar.FEBRUARY  : m = "02"; break;
            case Calendar.MARCH     : m = "03"; break;
            case Calendar.APRIL     : m = "04"; break;
            case Calendar.MAY       : m = "05"; break;
            case Calendar.JUNE      : m = "06"; break;
            case Calendar.JULY      : m = "07"; break;
            case Calendar.AUGUST    : m = "08"; break;
            case Calendar.SEPTEMBER : m = "09"; break;
            case Calendar.OCTOBER   : m = "10"; break;
            case Calendar.NOVEMBER  : m = "11"; break;
            case Calendar.DECEMBER  : m = "12"; break;
            default                 : m = "00";
        }

        return ("Records_" + y + m + d + "_" + h + n + s + ".txt");
    }
}
