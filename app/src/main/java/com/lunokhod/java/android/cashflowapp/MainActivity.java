package com.lunokhod.java.android.cashflowapp;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private CustomArrayAdapter spinnerAdapter;
    private TextView commentEditText;
    private TextView catField;
    private ImageView dateImageButton;
    private Calendar selectedDate;
    private EditText dateEditText;
    private EditText amountEditText;
    private DataManager dataManager;

    String[] arr = new String[] {"aaa1", "aaa2", "aaa3", "a444", "bbb1", "bbb2", "bbb4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (DataManager.getInstance() == null)
            dataManager = new DataManager();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Новая запись");

        selectedDate = Calendar.getInstance();

        dateEditText = (EditText)findViewById(R.id.dateEditText);
        amountEditText = (EditText)findViewById(R.id.amountEditText);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        dateImageButton = (ImageView)findViewById(R.id.dateImageButton);
        commentEditText = (TextView)findViewById(R.id.commentEditText);

        spinnerAdapter = new CustomArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, arr);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        amountEditText.addTextChangedListener(new NumericWatcher(amountEditText));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerAdapter.setSelected(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        commentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) commentEditText.setHint("");
                else commentEditText.setHint(R.string.comment_textview_hint);
            }
        });

        dateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFromPicker();
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFromPicker();
            }
        });

        setInitialDate();

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

    public DataManager getDataManager() {
        return dataManager;
    }

    private void setDateFromPicker() {
        new DatePickerDialog(MainActivity.this, d, selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    private void setInitialDate() {
        dateEditText.setText(DateUtils.formatDateTime(getApplicationContext(), selectedDate.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
}
