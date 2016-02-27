package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings_caption);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button dropDataBaseButton = (Button)findViewById(R.id.dropDataBaseButton);
        dropDataBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().dropDataBase();
            }
        });

        Button fillDataBaseButton = (Button)findViewById(R.id.fillDataBaseButton);
        fillDataBaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().fillInCategoryTable();
            }
        });

        Button deleteAllRecsButton = (Button)findViewById(R.id.deleteAllRecsButton);
        deleteAllRecsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().deleteAllRecords();
            }
        });

        final EditText catNumEditText = (EditText)findViewById(R.id.catNumEditText);
        Button getRecNumButton = (Button)findViewById(R.id.getRecNumButton);
        getRecNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;

                hideKeyboard(v);
                try {
                    i = Integer.parseInt(catNumEditText.getText().toString());
                }
                catch (NumberFormatException e) { }

                i = DataManager.getInstance().getRecordsNumWithCategory(i);
                showMsgDlg("Результат", "Количество записей = " + i);
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

    private void showMsgDlg(String title, String msg) {
        MessageDialog.newInstance(title, msg).show(getFragmentManager(), "messageDialog");
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
