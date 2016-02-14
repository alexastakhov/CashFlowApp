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

    ChargeRecord[] getAllRecords();

    void addCategory(String category, int prio);
    void deleteCategory(String category);
    void changeCategory(int categoryId, String newName, int prio);

    void addRecord(ChargeRecord record);
    void addRecord(float amount, CategoryItem category, String comment, Date date, int credit, int account);
    void deleteRecord(long recordId);
    void changeRecord();

    void dropDataBase();
    void fillInCategoryTable();
}
