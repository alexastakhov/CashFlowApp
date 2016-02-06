package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by alex on 06.01.2016.
 */
public class DataManager extends SQLiteOpenHelper implements DataManagerInterface {
    private static final String DATABASE_NAME = "cashflow.sqlite.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CATEGORY_TABLE = "category";
    private static final String RECORD_TABLE = "record";

    private static final String CATEGORY_COLUMN = "name";
    private static final String CATEGORY_GROUP_COLUMN = "group_name";
    private static final String PPIORITY_COLUMN = "priority";

    private static final String AMOUNT_COLUMN = "amount";
    private static final String DATETIME_COLUMN = "datetime";
    private static final String COMMENT_COLUMN = "comment";
    private static final String ACCOUNT_COLUMN = "account";
    private static final String DEBET_COLUMN = "debet";
    private static final String REC_CATEGORY_COLUMN = "category";

    private SQLiteDatabase database;

    private static DataManager instance = null;
    private ArrayList<CategoryItem> categories;

    @SuppressWarnings("unused")
    private static final String TAG = "DataManager";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        categories = new ArrayList<CategoryItem>();
        instance = this;

        testCategoryListView();
    }

    public static DataManager getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (iskDataBaseAvailable()) {
            try {
                openDatabase();
            } catch (SQLException e) {
            }
        } else {
            createTables(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIDEO_TABLE_NAME + ";");
        //createTable(sqLiteDatabase);
    }

    private boolean iskDataBaseAvailable() {
        SQLiteDatabase db = null;

        try {
            db = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //
        }

        if (db != null) {
            db.close();
        }

        return db != null ? true : false;
    }

    private void openDatabase() throws SQLException {
        database = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void createTables(SQLiteDatabase database) {
        String qs = "CREATE TABLE " + CATEGORY_TABLE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CATEGORY_COLUMN + " TEXT, " +
                CATEGORY_GROUP_COLUMN + " TEXT, " +
                PPIORITY_COLUMN + " INTEGER);";
        database.execSQL(qs);

        qs = "CREATE TABLE " + RECORD_TABLE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AMOUNT_COLUMN + " REAL, " +
                DATETIME_COLUMN + " TEXT, " +
                COMMENT_COLUMN + " TEXT, " +
                ACCOUNT_COLUMN + " INTEGER, " +
                DEBET_COLUMN + " INTEGER, " +
                REC_CATEGORY_COLUMN + " INTEGER);";
        database.execSQL(qs);
    }

    public CategoryItem[] getCategories() {
        return categories.toArray(new CategoryItem[categories.size()]);
    }

    public CategoryItem[] getCategoriesSortedByName() {
        List<CategoryItem> result = new ArrayList<CategoryItem>();

        for (CategoryItem item : categories)
            result.add(item);

        Collections.sort(result, new Comparator<CategoryItem>() {
            @Override
            public int compare(CategoryItem lhs, CategoryItem rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });

        return result.toArray(new CategoryItem[result.size()]);
    }

    public String[] getCategoriesAsStrings() {
        String[] result = new String[categories.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = categories.get(i).getName();
        }

        return result;
    }

    public void addCategory(String category, boolean prio) {
        for (int i = 0; i < categories.size(); i++)
            if (categories.get(i).getName() == category) return;

        categories.add(new CategoryItem(category, prio));
    }

    public void deleteCategory(String category) {
        for (int i = 0; i < categories.size(); i++)
            if (categories.get(i).getName() == category) {
                categories.remove(i);
                return;
            }
    }

    private void fillInCategoryTable() {

    }

    private void testCategoryListView() {
        categories.add(new CategoryItem("Первая", false));
        categories.add(new CategoryItem("Вторая", false));
        categories.add(new CategoryItem("Третья", false));
        categories.add(new CategoryItem("Четвертая", true));
        categories.add(new CategoryItem("Пятая", false));
        categories.add(new CategoryItem("Шестая", true));
        categories.add(new CategoryItem("Седьмая", true));
        categories.add(new CategoryItem("Восьмая", true));
        categories.add(new CategoryItem("Девятая", false));
        categories.add(new CategoryItem("Десятая", true));
        categories.add(new CategoryItem("Одиннадцатая", false));
        categories.add(new CategoryItem("Двенадцатая", true));
        categories.add(new CategoryItem("Тринадцатая", true));
        categories.add(new CategoryItem("Четырнадцатая", true));
    }
}