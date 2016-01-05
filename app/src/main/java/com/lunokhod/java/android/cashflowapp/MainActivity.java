package com.lunokhod.java.android.cashflowapp;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private TextView commentEditText;
    private TextView catField;

    String[] arr = new String[] {"aaa1", "aaa2", "aaa3", "a444", "bbb1", "bbb2", "bbb4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Новая запись");

        spinnerAdapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, android.R.id.text1, arr);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        categorySpinner.setAdapter(spinnerAdapter);

        commentEditText = (TextView)findViewById(R.id.commentEditText);
        commentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) commentEditText.setHint("");
                else commentEditText.setHint(R.string.comment_textview_hint);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_categories) {
            intent.setClass(getApplicationContext(), CategoryActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_records) {
            intent.setClass(getApplicationContext(), RecordsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
