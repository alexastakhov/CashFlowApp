package com.lunokhod.java.android.cashflowapp;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        recordListViewAdapter = new RecordListViewAdapter(this.getApplicationContext(), dataManager.getAllRecords());
        recordListView = (ListView)findViewById(R.id.recordListView);
        recordListView.setAdapter(recordListViewAdapter);

        recordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemEditDialog(recordListViewAdapter.getItem(position));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void showItemEditDialog(ChargeRecord item) {
        RecordEditDialog.getInstance(item).show(getFragmentManager(), "recordDialog");
    }

    private void updateListAdapter() {
        recordListViewAdapter.setDataObjects(dataManager.getAllRecords());
    }

    public void changeRecord(int categoryId, String newName, int prio) {
        //dataManager.changeRecord(categoryId, newName, prio);
        updateListAdapter();
//        Toast.makeText(CategoryActivity.this, R.string.category_dialog_item_saved, Toast.LENGTH_SHORT).show();
    }

    public void deleteRecord(long recordId) {
        dataManager.deleteRecord(recordId);
        updateListAdapter();
//        Toast.makeText(CategoryActivity.this, R.string.category_dialog_item_deleted, Toast.LENGTH_SHORT).show();
    }
}

// TODO: Create record edit dialog
// TODO: Create filers: Date-Date, Last period, Category
// TODO: Reading records into own thread
