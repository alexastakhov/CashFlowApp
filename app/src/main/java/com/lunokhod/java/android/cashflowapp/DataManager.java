package com.lunokhod.java.android.cashflowapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DataManager extends SQLiteOpenHelper implements IDataManager {
    private static final String DATABASE_NAME = "cashflow.sqlite.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CATEGORY_TABLE = "category";
    private static final String RECORD_TABLE = "record";

    private static final String NAME_COLUMN = "name";
    private static final String GROUP_COLUMN = "group_name";
    private static final String PRIORITY_COLUMN = "priority";

    private static final String FULL_CATEGORY_ROW = BaseColumns._ID + "," +
            NAME_COLUMN + "," + GROUP_COLUMN + "," + PRIORITY_COLUMN;

    private static final String AMOUNT_COLUMN = "amount";
    private static final String DATETIME_COLUMN = "datetime";
    private static final String COMMENT_COLUMN = "comment";
    private static final String ACCOUNT_COLUMN = "account";
    private static final String CREDIT_COLUMN = "credit";
    private static final String CATEGORY_COLUMN = "category";

    private static final String FULL_RECORD_ROW = BaseColumns._ID + "," + AMOUNT_COLUMN + "," +
            DATETIME_COLUMN + "," + COMMENT_COLUMN + "," + ACCOUNT_COLUMN + "," +
            CREDIT_COLUMN + "," + CATEGORY_COLUMN;

    private static DataManager instance = null;
    private final Context context;
    private ArrayList<CategoryItem> tmp_categories = new ArrayList<>();

    @SuppressWarnings("unused")
    private static final String TAG = "DataManager";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        instance = this;

        Log.i(TAG, "DataManager()");
    }

    public static DataManager getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate()");

        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldv, int newv) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE + ";");
        //createTable(sqLiteDatabase);
    }

    private boolean isDataBaseAvailable() {
        SQLiteDatabase database = null;

        try {
            database = this.getReadableDatabase();
        } catch (SQLiteException e) {
            Log.i(TAG, "iskDataBaseAvailable() : Not available");
            Log.i(TAG, "iskDataBaseAvailable() Exception: " + e.getMessage());
        }

        if (database != null) {
            Log.i(TAG, "iskDataBaseAvailable() : Available");
            database.close();
        }

        return database != null;
    }

    private void createTables(SQLiteDatabase database) {
        String sql;

        try {
            sql = "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT, " +
                    GROUP_COLUMN + " TEXT, " +
                    PRIORITY_COLUMN + " INTEGER);";
            database.execSQL(sql);
            Log.i(TAG, "SQLite : " + sql);
        }
        catch (android.database.SQLException e) {
            Log.i(TAG, "createTables() Exception: " + e.getMessage());
        }

        try {
            sql = "CREATE TABLE " + RECORD_TABLE + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AMOUNT_COLUMN + " REAL, " +
                    DATETIME_COLUMN + " INTEGER, " +
                    COMMENT_COLUMN + " TEXT, " +
                    ACCOUNT_COLUMN + " INTEGER, " +
                    CREDIT_COLUMN + " INTEGER, " +
                    CATEGORY_COLUMN + " INTEGER);";
            database.execSQL(sql);
            Log.i(TAG, "SQLite : " + sql);
        }
        catch (android.database.SQLException e) {
            Log.i(TAG, "createTables() Exception: " + e.getMessage());
        }
    }

    private ArrayList<CategoryItem> readCategoryData() {
        Cursor cursor;
        String sql = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase database;
        ArrayList<CategoryItem> result = new ArrayList<>();

        Log.i(TAG, "readCategoryData()");
        Log.i(TAG, "SQLite : " + sql);

        if (isDataBaseAvailable()) {
            database = this.getWritableDatabase();
            cursor = database.rawQuery(sql, null);

            Log.i(TAG, "readCategoryData() cursor.getCount() == " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    result.add(new CategoryItem(cursor.getString(1), cursor.getString(2),
                            cursor.getInt(3), cursor.getInt(0)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        }
        return result;
    }

    public CategoryItem[] getCategories() {
        ArrayList<CategoryItem> categories = readCategoryData();
        return categories.toArray(new CategoryItem[categories.size()]);
    }

    public CategoryItem[] getCategoriesSortedByName() {
        List<CategoryItem> result = new ArrayList<>();
        ArrayList<CategoryItem> categories = readCategoryData();

        Log.i(TAG, "getCategoriesSortedByName()");

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
        ArrayList<CategoryItem> categories = readCategoryData();
        String[] result = new String[categories.size()];

        Log.i(TAG, "getCategoriesAsStrings()");

        for (int i = 0; i < result.length; i++) {
            result[i] = categories.get(i).getName();
        }

        return result;
    }

    public CategoryItem getCategoryByName(String name) {
        CategoryItem result = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + NAME_COLUMN + "=\"" + name + "\";";
        Cursor cursor;

        if (database != null) {
            cursor = database.rawQuery(sql, null);

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                result = new CategoryItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(0));
            }
            cursor.close();
            database.close();
        }

        Log.i(TAG, "getCategoryByName(" + name + ")");
        Log.i(TAG, "SQLite : " + sql);

        return result;
    }

    public CategoryItem getCategoryById(int id) {
        CategoryItem result = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + BaseColumns._ID + "=\"" + id + "\";";
        Cursor cursor;

        if (database != null) {
            cursor = database.rawQuery(sql, null);

            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                result = new CategoryItem(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(0));
            }
            cursor.close();
            database.close();
        }

        Log.i(TAG, "getCategoryById(" + id + ")");
        Log.i(TAG, "SQLite : " + sql);

        return result;
    }

    public ChargeRecord[] getAllRecords() {
        Cursor cursor;
        String sql = "SELECT * FROM " + RECORD_TABLE;
        SQLiteDatabase database;
        ArrayList<ChargeRecord> result = new ArrayList<>();

        Log.i(TAG, "getAllRecords()");
        Log.i(TAG, "SQLite : " + sql);

        if (isDataBaseAvailable()) {
            database = this.getWritableDatabase();
            cursor = database.rawQuery(sql, null);

            Log.i(TAG, "getAllRecords() cursor.getCount() == " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    result.add(new ChargeRecord(
                            cursor.getFloat(1),
                            getCategoryById(cursor.getInt(6)),
                            cursor.getString(3),
                            (new Date(cursor.getLong(2))),
                            cursor.getInt(5),
                            cursor.getInt(4),
                            cursor.getInt(0)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();

            Collections.sort(result, new Comparator<ChargeRecord>() {
                @Override
                public int compare(ChargeRecord lhs, ChargeRecord rhs) {
                    return lhs.getDate().compareTo(rhs.getDate());
                }
            });
        }
        return result.toArray(new ChargeRecord[result.size()]);
    }

    public int getRecordsNumWithCategory(int categoryId) {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + RECORD_TABLE + " WHERE " +
                CATEGORY_COLUMN + "=" + categoryId + ";";
        Cursor cursor;
        int result = 0;

        if (database != null) {
            cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            result = cursor.getInt(0);

            cursor.close();
            database.close();
        }
        return result;
    }

    public void addCategory(String name, int prio) {
        String sql = "INSERT OR IGNORE INTO " + CATEGORY_TABLE + " (" + FULL_CATEGORY_ROW +
                ") VALUES (NULL, ?, ?, ?);";
        Object[] bindArgs = new Object[]{name, "", prio};
        SQLiteDatabase database = this.getWritableDatabase();

        Log.i(TAG, "addCategory(" + name + ", " + prio + ")");
        Log.i(TAG, "SQLite : " + sql);

        database.execSQL(sql, bindArgs);
        database.close();
    }

    public void deleteCategory(String name) {
        String sql = "DELETE FROM " + CATEGORY_TABLE + " WHERE " + NAME_COLUMN + "=\"" + name + "\";";
        SQLiteDatabase database = this.getWritableDatabase();

        Log.i(TAG, "deleteCategory(" + name + ")");
        Log.i(TAG, "SQLite : " + sql);

        database.execSQL(sql);
        database.close();
    }

    public void changeCategory(int categoryId, String newName, int prio) {
        String sql = "UPDATE " + CATEGORY_TABLE + " SET " + NAME_COLUMN + "= ?," + GROUP_COLUMN + "=\"\"," + PRIORITY_COLUMN +
                "= ? WHERE " + BaseColumns._ID + "= ?";
        Object[] bindArgs = new Object[]{newName, prio, categoryId};
        SQLiteDatabase database = this.getWritableDatabase();

        Log.i(TAG, "changeCategory(" + categoryId + ", " + newName + ", " + prio + ")");
        Log.i(TAG, "SQLite : " + sql);

        database.execSQL(sql, bindArgs);
        database.close();
    }

    public void addRecord(ChargeRecord record) {
        addRecord(record.getAmount(), record.getCategory(), record.getComment(),
                record.getDate(), record.getCredit(), record.getAccount());
    }

    public void addRecord(float amount, CategoryItem category, String comment, Date date, int credit, int account) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String sql = "INSERT INTO " + RECORD_TABLE + "(" + FULL_RECORD_ROW + ") VALUES (NULL, ?, ?, ?, ?, ?, ?);";
        Object[] bindArgs = new Object[]{amount, date.getTime(), comment, account, credit, category.getId()};
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL(sql, bindArgs);
        database.close();

        Log.i(TAG, "addRecord()");
        Log.i(TAG, "amount = " + amount);
        Log.i(TAG, "category = " + category.getName() + "(" + category.getId() + ")");
        Log.i(TAG, "comment = " + comment);
        Log.i(TAG, "date = " + dateFormat.format(date) + " (" + date.getTime() +")");
        Log.i(TAG, "credit = " + credit);
    }

    public void deleteRecord(long recordId) {
        String sql = "DELETE FROM " + RECORD_TABLE + " WHERE " + BaseColumns._ID + "=\"" + recordId + "\";";
        SQLiteDatabase database = this.getWritableDatabase();

        Log.i(TAG, "deleteRecord(" + recordId + ")");
        Log.i(TAG, "SQLite : " + sql);

        database.execSQL(sql);
        database.close();
    }

    public void changeRecord() {

    }

    public void clearDataBase() {
        SQLiteDatabase database = this.getWritableDatabase();

        if (database != null) {
            Log.i(TAG, "clearDataBase()");

            database.delete(CATEGORY_TABLE, null, null);
            database.delete(RECORD_TABLE, null, null);
            database.close();
        }
    }

    public void deleteAllRecords() {
        SQLiteDatabase database = this.getWritableDatabase();

        if (database != null) {
            Log.i(TAG, "deleteAllRecords()");

            database.delete(RECORD_TABLE, null, null);
            database.close();
        }
    }

    public void dropDataBase() {
        SQLiteDatabase database = this.getReadableDatabase();

        if (database != null) {
            Log.i(TAG, "dropDataBase()");

            context.deleteDatabase(DATABASE_NAME);
            database.close();
        }
    }

    public void fillInCategoryTable() {
        testCategoryListView();

        Log.i(TAG, "fillInCategoryTable()");

        try {
            SQLiteDatabase database = this.getWritableDatabase();

            if (database != null) {
                database.execSQL("DELETE FROM " + CATEGORY_TABLE);

                for (CategoryItem item : tmp_categories) {
                    String sql = "INSERT INTO " + CATEGORY_TABLE + "(_id," + NAME_COLUMN +
                            "," + GROUP_COLUMN + "," + PRIORITY_COLUMN + ") VALUES (NULL, ?, ?, ?)";
                    Object[] bindArgs = new Object[]{item.getName(), "", item.getPriority()};
                    database.execSQL(sql, bindArgs);
                    Log.i(TAG, sql);
                }
                database.close();
            }
        } catch (android.database.SQLException e) {
            Log.i(TAG, "fillInCategoryTable() Exception: " + e.getMessage());
        }
    }

    private void testCategoryListView() {
        tmp_categories.add(new CategoryItem("Первая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Вторая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Третья", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Четвертая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Пятая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Шестая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Седьмая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Восьмая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Девятая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Десятая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Одиннадцатая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Двенадцатая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Тринадцатая", CategoryItem.LOW_PRIO));
        tmp_categories.add(new CategoryItem("Четырнадцатая", CategoryItem.LOW_PRIO));
    }
}