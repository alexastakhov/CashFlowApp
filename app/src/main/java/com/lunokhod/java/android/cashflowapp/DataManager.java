package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

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
    private Context context;
    private ArrayList<CategoryItem> categories = new ArrayList<CategoryItem>();
    private ArrayList<CategoryItem> tmp_categories = new ArrayList<CategoryItem>();

    @SuppressWarnings("unused")
    private static final String TAG = "DataManager";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        instance = this;

        //fillInCategoryTable();
        readData();

        Log.i(TAG, "DataManager()");
    }

    public static DataManager getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "SQLiteOpenHelper->onCreate()");

        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIDEO_TABLE_NAME + ";");
        //createTable(sqLiteDatabase);
    }

    public boolean isDataBaseAvailable() {
        database = null;

        try {
            database = this.getReadableDatabase();
        } catch (SQLiteException e) {
            Log.i(TAG, "DataManager->iskDataBaseAvailable() : Not available");
            Log.i(TAG, "DataManager->iskDataBaseAvailable() Exception: " + e.getMessage());
        }

        if (database != null) {
            Log.i(TAG, "DataManager->iskDataBaseAvailable() : Available");
            database.close();
        }

        return database != null ? true : false;
    }

    private void createTables(SQLiteDatabase database) {
        String sql;

        try {
            sql = "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_COLUMN + " TEXT, " +
                    CATEGORY_GROUP_COLUMN + " TEXT, " +
                    PPIORITY_COLUMN + " INTEGER);";
            database.execSQL(sql);
        }
        catch (android.database.SQLException e) {
            Log.i(TAG, "DataManager->createTables() Exception: " + e.getMessage());
        }

        try {
            sql = "CREATE TABLE " + RECORD_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AMOUNT_COLUMN + " REAL, " +
                    DATETIME_COLUMN + " TEXT, " +
                    COMMENT_COLUMN + " TEXT, " +
                    ACCOUNT_COLUMN + " INTEGER, " +
                    DEBET_COLUMN + " INTEGER, " +
                    REC_CATEGORY_COLUMN + " INTEGER);";
            database.execSQL(sql);
        }
        catch (android.database.SQLException e) {
            Log.i(TAG, "DataManager->createTables() Exception: " + e.getMessage());
        }
    }

    private void readData() {
        Cursor cursor;
        String sql = "SELECT * FROM " + CATEGORY_TABLE;

        Log.i(TAG, "readData()");

        if (isDataBaseAvailable()) {
            database = this.getWritableDatabase();
            cursor = database.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                Log.i(TAG, "readData() cursor.getCount() == " + cursor.getCount());

                do {
                    categories.add(new CategoryItem(cursor.getString(1),
                            (cursor.getInt(3) == 1 ? true : false)));
                } while (cursor.moveToNext());
            }
        }
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

    public void dropDataBase() {
        database = this.getWritableDatabase();

        if (database != null) {
            Log.i(TAG, "DataManager->dropDataBase()");
            context.deleteDatabase(DATABASE_NAME);
        }
    }

    private void fillInCategoryTable() {
        testCategoryListView();

        try {
            database = this.getWritableDatabase();

            if (database != null) {
                database.execSQL("DELETE FROM " + CATEGORY_TABLE);

                for (CategoryItem item : tmp_categories) {
                    String sql = "INSERT INTO " + CATEGORY_TABLE + "(_id, " + CATEGORY_COLUMN +
                            ", " + CATEGORY_GROUP_COLUMN + ", " + PPIORITY_COLUMN + ") VALUES (NULL, ?, ?, ?)";
                    Object[] bindArgs = new Object[]{item.getName(), "", (item.getPriority() ? 1 : 0)};
                    database.execSQL(sql, bindArgs);
                }
            }
        } catch (android.database.SQLException e) {
            Log.i(TAG, "DataManager->fillInCategoryTable() Exception: " + e.getMessage());
        }
    }

    private void testCategoryListView() {
        tmp_categories.add(new CategoryItem("Первая", false));
        tmp_categories.add(new CategoryItem("Вторая", false));
        tmp_categories.add(new CategoryItem("Третья", false));
        tmp_categories.add(new CategoryItem("Четвертая", true));
        tmp_categories.add(new CategoryItem("Пятая", false));
        tmp_categories.add(new CategoryItem("Шестая", true));
        tmp_categories.add(new CategoryItem("Седьмая", true));
        tmp_categories.add(new CategoryItem("Восьмая", true));
        tmp_categories.add(new CategoryItem("Девятая", false));
        tmp_categories.add(new CategoryItem("Десятая", true));
        tmp_categories.add(new CategoryItem("Одиннадцатая", false));
        tmp_categories.add(new CategoryItem("Двенадцатая", true));
        tmp_categories.add(new CategoryItem("Тринадцатая", true));
        tmp_categories.add(new CategoryItem("Четырнадцатая", true));
    }
}