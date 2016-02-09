package com.lunokhod.java.android.cashflowapp;

/**
 * Created by alex on 08.01.2016.
 */
public class CategoryItem {
    public static final int LOW_PRIO = 0;
    public static final int HIGH_PRIO = 1;

    private String name;
    private String groupName;
    private int priority;

    @SuppressWarnings("unused")
    private static final String TAG = "CategoryItem";

    public CategoryItem() {
        name = "";
        groupName = "";
        this.priority = LOW_PRIO;
    }

    public CategoryItem(String name, String groupName, int priority) {
        this.name = name;
        this.groupName = groupName;
        this.priority = priority;
    }

    public CategoryItem(String name, int priority) {
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

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
