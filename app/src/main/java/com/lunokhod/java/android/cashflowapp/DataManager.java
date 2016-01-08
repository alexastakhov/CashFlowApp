package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.util.ArrayList;

/**
 * Created by alex on 06.01.2016.
 */
public class DataManager {
    private static DataManager lastDataManager = null;

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
    private static final String CREDIT_COLUMN = "credit";
    private static final String REC_CATEGORY_COLUMN = "category";

    private ArrayList<CategoryItem> categories;
    //private ArrayList<CategoryItem> p_categories;

    public DataManager() {
        categories = new ArrayList<CategoryItem>();
        //p_categories = new ArrayList<CategoryItem>();

        lastDataManager = this;
    }

    public static DataManager getInstance() {
        return lastDataManager;
    }

    public CategoryItem[] getCategories() {
        return (CategoryItem[])categories.toArray();
    }

    public String[] getCategoriesAsStrings() {
        String[] result = new String[categories.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = categories.get(i).getName();
        }

        return result;
    }

    public void insertCategory(String category, boolean pri) {
        for (int i = 0; i < categories.size(); i++)
            if (categories.get(i).getName() == category) return;

        categories.add(new CategoryItem(category, pri));
    }

    public void deleteCategory(String category) {
        for (int i = 0; i < categories.size(); i++)
            if (categories.get(i).getName() == category) {
                categories.remove(i);
                return;
            }
    }

    public void clearCategory() {
        categories.clear();
    }

    private static class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            CreateTables(sqLiteDatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv)
        {
            //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VIDEO_TABLE_NAME + ";");
            //createTable(sqLiteDatabase);
        }

        private void CreateTables(SQLiteDatabase sqLiteDatabase) {
            String qs = "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_COLUMN + " TEXT, " +
                    CATEGORY_GROUP_COLUMN + " TEXT, " +
                    PPIORITY_COLUMN + " INTEGER);";
            sqLiteDatabase.execSQL(qs);

            qs = "CREATE TABLE " + RECORD_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AMOUNT_COLUMN + " REAL, " +
                    DATETIME_COLUMN + " TEXT, " +
                    COMMENT_COLUMN + " TEXT, " +
                    ACCOUNT_COLUMN + " INTEGER, " +
                    CREDIT_COLUMN + " INTEGER, " +
                    REC_CATEGORY_COLUMN + " INTEGER);";
            sqLiteDatabase.execSQL(qs);
        }
    }
}

