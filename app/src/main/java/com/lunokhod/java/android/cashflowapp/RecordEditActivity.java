package com.lunokhod.java.android.cashflowapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RecordEditActivity extends AppCompatActivity {
    private Spinner editCategorySpinner;
    private SpinArrayAdapter spinnerAdapter;
    private TextView editCommentEditText;
    private ImageView editDateImageButton;
    private Calendar selectedDate;
    private EditText editDateEditText;
    private EditText editAmountEditText;
    private IDataManager dataManager;
    private ArrayList<String> spinnerList;
    private Button editSaveRecButton;
    private long recId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        spinnerList = new ArrayList<String>(Arrays.asList(DataManager.getInstance().getCategoriesAsStrings()));
        spinnerAdapter = new SpinArrayAdapter(getApplicationContext(), R.layout.edit_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(R.layout.edit_spinner_dropdown_item);

        editCategorySpinner = (Spinner)findViewById(R.id.editCategorySpinner);
        editCommentEditText = (TextView)findViewById(R.id.editCommentEditText);
        editDateImageButton = (ImageView)findViewById(R.id.editDateImageButton);
        editDateEditText = (EditText)findViewById(R.id.editDateEditText);
        editAmountEditText = (EditText)findViewById(R.id.editAmountEditText);
        editSaveRecButton = (Button)findViewById(R.id.editSaveRecButton);

        editAmountEditText.addTextChangedListener(new NumericWatcher(editAmountEditText));
        editCategorySpinner.setAdapter(spinnerAdapter);
        editCategorySpinner.setSelection(spinnerAdapter.getPosition(intent.getStringExtra("category")));

        editDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFromPicker();
            }
        });

        editDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateFromPicker();
                hideKeyboard(v);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.record_edit_caption);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        selectedDate = Calendar.getInstance();
        selectedDate.setTimeInMillis(intent.getLongExtra("date", selectedDate.getTimeInMillis()));
        setInitialDate();

        editAmountEditText.setText(formatAmount(intent.getFloatExtra("amount", 0), intent.getIntExtra("credit", 0)));
        editCommentEditText.setText(intent.getStringExtra("comment"));
        recId = intent.getIntExtra("id", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record_edit, menu);

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

        if (id == R.id.action_delete) {
            //intent.setClass(getApplicationContext(), SettingsActivity.class);
            //startActivity(intent);
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void setDateFromPicker() {
        new DatePickerDialog(this, d, selectedDate.get(Calendar.YEAR),
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
        editDateEditText.setText(DateUtils.formatDateTime(getApplicationContext(), selectedDate.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

class SpinArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;

    public SpinArrayAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, resource, null);
        TextView textView = (TextView)convertView;

        textView.setText(getItem(position));

        return convertView;
    }
}
