package com.lunokhod.java.android.cashflowapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RecordsActivity extends AppCompatActivity {
    private RecordListViewAdapter recordListViewAdapter;
    private ListView recordListView;
    private IDataManager dataManager;

    @SuppressWarnings("unused")
    private static final String TAG = "RecordsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = DataManager.getInstance();

        recordListViewAdapter = new RecordListViewAdapter(this.getApplicationContext());
        recordListView = (ListView)findViewById(R.id.recordListView);
        recordListView.setAdapter(recordListViewAdapter);

        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                ChargeRecord item = recordListViewAdapter.getItem(position);

                intent.putExtra("amount", item.getAmount());
                intent.putExtra("credit", item.getCredit());
                intent.putExtra("category", item.getCategory().getName());
                intent.putExtra("date", item.getDate().getTime());
                intent.putExtra("comment", item.getComment());
                intent.putExtra("account", item.getAccount());
                intent.putExtra("id", item.getId());

                intent.setClass(getApplicationContext(), RecordEditActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_records);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListAdapter();
        Log.i(TAG, "onStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_records, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateListAdapter() {
        recordListViewAdapter.setDataObjects(dataManager.getAllRecords());
    }
}

// TODO: Create filers: Date-Date, Last period, Category
// TODO: Reading records into own thread
