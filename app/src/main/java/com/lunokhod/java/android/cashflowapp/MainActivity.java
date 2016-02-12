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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private CustomArrayAdapter spinnerAdapter;
    private TextView commentEditText;
    private ImageView dateImageButton;
    private Calendar selectedDate;
    private EditText dateEditText;
    private EditText amountEditText;
    private IDataManager dataManager;
    private ArrayList<String> spinnerList;
    private Button addRecButton;

    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = new DataManager(getApplicationContext());
        spinnerList = new ArrayList<String>(Arrays.asList(dataManager.getCategoriesAsStrings()));
        ActionBar actionBar = getSupportActionBar();
        selectedDate = Calendar.getInstance();

        if (actionBar != null) actionBar.setTitle(R.string.main_caption);

        dateEditText = (EditText)findViewById(R.id.dateEditText);
        amountEditText = (EditText)findViewById(R.id.amountEditText);
        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
        dateImageButton = (ImageView)findViewById(R.id.dateImageButton);
        commentEditText = (TextView)findViewById(R.id.commentEditText);
        addRecButton = (Button)findViewById(R.id.addRecButton);

        spinnerAdapter = new CustomArrayAdapter(this, R.layout.spinner_item, android.R.id.text1, spinnerList);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        amountEditText.addTextChangedListener(new NumericWatcher(amountEditText));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerAdapter.setSelected(true);
                Log.i(TAG, "onItemSelected()");
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

        commentEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                    return true;
                return false;
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

        addRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChargeRecord();
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
    protected void onStart() {
        super.onStart();
        updateSpinnerData();
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
            intent.setClass(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
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

    private void updateSpinnerData() {
        String[] array = dataManager.getCategoriesAsStrings();

        spinnerList.clear();

        for (int i = 0; i < array.length; i++)
            spinnerList.add(array[i]);

        Collections.sort(spinnerList);

        spinnerAdapter.setSelected(false);
        spinnerAdapter.setNextSwitched();
        categorySpinner.setSelection(0);
    }

    private void addChargeRecord() {
        String str;
        float amount;
        Date date;
        CategoryItem category;
        String comment;
        int credit = ChargeRecord.DEBET;

        str = amountEditText.getText().toString();

        if (str.length() == 0) {
            showMsgDlg("Ошибка", "Поле суммы не может быть пустым. Введите расход/доход.");
            return;
        }

        if (str.contains("+")) credit = ChargeRecord.CREDIT;

        try {
            str = str.replace((new String(new char[] { 160 })), "").replace("+", "");
            amount = Float.parseFloat(str);
        }
        catch (NumberFormatException e) {
            showMsgDlg("Ошибка", "Неправильный формат поля Amount");
            return;
        }

        date = selectedDate.getTime();
        comment = commentEditText.getText().toString();
        category = dataManager.getCategoryByName(categorySpinner.getSelectedItem().toString());

        if (category == null) {
            showMsgDlg("Ошибка", "Необходимо выбрать категорию расходов/доходов");
            return;
        }

        dataManager.addRecord(amount, category, comment, date, credit, 0);
        Toast.makeText(this, R.string.main_activity_record_added, Toast.LENGTH_SHORT).show();
    }

    private void showMsgDlg(String title, String msg) {
        MessageDialog.newInstance(title, msg).show(getFragmentManager(), "messageDialog");
    }

    private void clearForm() {

    }
}
