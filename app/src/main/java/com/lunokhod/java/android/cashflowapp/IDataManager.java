package com.lunokhod.java.android.cashflowapp;

import java.util.Date;

/**
 * Created by alex on 06.02.2016.
 */
public interface IDataManager {
    CategoryItem[] getCategories();
    CategoryItem[] getCategoriesSortedByName();
    String[] getCategoriesAsStrings();
    CategoryItem getCategoryByName(String name);
    CategoryItem getCategoryById(int id);
    int getRecordsNumWithCategory(int categoryId);

    ChargeRecord[] getAllRecords();

    void addCategory(String category, int priority);
    void deleteCategory(int  categoryId);
    void deleteCategory(String  name);
    void changeCategory(int categoryId, String newName, int priority);

    void addRecord(ChargeRecord record);
    void addRecord(float amount, int  categoryId, String comment, Date date, int credit, int account);
    void deleteRecord(long recordId);
    void changeRecord(long recId, float amount, int  categoryId, String comment, Date date, int credit, int account);

    void dropDataBase();
    void fillInCategoryTable();
}
