package com.lunokhod.java.android.cashflowapp;

/**
 * Created by alex on 08.01.2016.
 */
public class CategoryItem {
    private String name;
    private String groupName;
    private boolean priority;

    @SuppressWarnings("unused")
    private static final String TAG = "CategoryItem";

    public CategoryItem() {
        name = "";
        groupName = "";
        this.priority = false;
    }

    public CategoryItem(String groupName, String name, boolean priority) {
        this.name = name;
        this.groupName = groupName;
        this.priority = priority;
    }

    public CategoryItem(String name, boolean priority) {
        this.name = name;
        this.groupName = "";
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
        else {
            this.name = "";
        }
    }

    public void setGroupName(String groupName) {
        if (name != null) {
            this.groupName = groupName;
        }
        else {
            this.groupName = "";
        }
    }

    public void setPriority(boolean priority) {
        priority = priority;
    }

    public boolean getPriority() {
        return priority;
    }
}
