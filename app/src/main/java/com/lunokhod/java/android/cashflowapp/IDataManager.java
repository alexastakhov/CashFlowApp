package com.lunokhod.java.android.cashflowapp;

/**
 * Created by alex on 06.02.2016.
 */
public interface IDataManager {
    CategoryItem[] getCategories();
    CategoryItem[] getCategoriesSortedByName();
    String[] getCategoriesAsStrings();
    CategoryItem getCategoryByName(String name);

    void addCategory(String category, int prio);
    void deleteCategory(String category);
    void changeCategory(String oldName, String newName, int prio);

    void addRecord();
    void deleteRecord();
    void changeRecord();

    void dropDataBase();
    void fillInCategoryTable();
}
