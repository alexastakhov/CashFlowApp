package com.lunokhod.java.android.cashflowapp;

/**
 * Created by alex on 06.02.2016.
 */
public interface IDataManager {
    CategoryItem[] getCategories();
    CategoryItem[] getCategoriesSortedByName();
    String[] getCategoriesAsStrings();

    void addCategory(String category, boolean prio);
    void deleteCategory(String category);
    void changeCategory(String oldName, String newName, boolean prio);

    void dropDataBase();

    void fillInCategoryTable();
}
