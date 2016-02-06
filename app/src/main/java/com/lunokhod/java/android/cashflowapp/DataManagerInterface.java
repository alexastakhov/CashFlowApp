package com.lunokhod.java.android.cashflowapp;

/**
 * Created by alex on 06.02.2016.
 */
public interface DataManagerInterface {
    CategoryItem[] getCategories();
    CategoryItem[] getCategoriesSortedByName();
    String[] getCategoriesAsStrings();
    void addCategory(String category, boolean prio);
    void deleteCategory(String category);
}
