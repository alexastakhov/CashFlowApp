package com.lunokhod.java.android.cashflowapp;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CategoryActivity extends AppCompatActivity {
    private CategoryListViewAdapter categoryListViewAdapter;
    private ListView categoryListView;
    private IDataManager dataManager;

    @SuppressWarnings("unused")
    private static final String TAG = "CategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = DataManager.getInstance();

        categoryListViewAdapter = new CategoryListViewAdapter(this.getApplicationContext());
        categoryListView = (ListView)findViewById(R.id.categoryListView);
        categoryListView.setAdapter(categoryListViewAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemEditDialog(categoryListViewAdapter.getItem(position));
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_categories);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItemNewDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListAdapter();
        Log.i(TAG, "onStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
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

    private void showItemEditDialog(CategoryItem item) {
        CategoryEditDialog.getInstance(item).show(getFragmentManager(), "categoryDialog");
    }

    private void showItemNewDialog() {
        CategoryNewDialog.getInstance().show(getFragmentManager(), "categoryDialog");
    }

    private void updateListAdapter() {
        categoryListViewAdapter.setDataObjects(dataManager.getCategoriesSortedByName());
    }

    public boolean isCategoryNameExists(String name) {
        for (String s : dataManager.getCategoriesAsStrings())
            if (name.equals(s))
                return true;
        return false;
    }

    public void changeCategory(int categoryId, String newName, int prio) {
        dataManager.changeCategory(categoryId, newName, prio);
        updateListAdapter();
        Toast.makeText(CategoryActivity.this, R.string.category_dialog_item_saved, Toast.LENGTH_SHORT).show();
    }

    public void addCategory(String name, int priority) {
        if (!isCategoryNameExists(name)) {
            dataManager.addCategory(name, priority);
            updateListAdapter();
            Toast.makeText(this, R.string.category_dialog_item_added, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, R.string.debug_error_category_exists, Toast.LENGTH_SHORT).show();
    }

    public void deleteCategory(int categoryId) {
        if (dataManager.getRecordsNumWithCategory(categoryId) == 0) {
            dataManager.deleteCategory(categoryId);
            updateListAdapter();
            Toast.makeText(CategoryActivity.this, R.string.category_dialog_item_deleted, Toast.LENGTH_SHORT).show();
        }
        else {
            showMsgDlg(R.string.message_dialog_header_warning, R.string.message_dialog_msg_category_in_use);
        }
    }

    private void showMsgDlg(int title, int msg) {
        MessageDialog.newInstance(getResources().getString(title), getResources().getString(msg)).show(getFragmentManager(), "messageDialog");
    }
}

// TODO: Perform Category using check before deleting