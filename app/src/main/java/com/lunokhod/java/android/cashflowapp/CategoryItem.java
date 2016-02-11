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
    private int id;

    @SuppressWarnings("unused")
    private static final String TAG = "CategoryItem";

    public CategoryItem() {
        name = "";
        groupName = "";
        this.priority = LOW_PRIO;
        id = 0;
    }

    public CategoryItem(String name, String groupName, int priority) {
        this.name = name;
        this.groupName = groupName;
        this.priority = priority;
        id = 0;
    }

    public CategoryItem(String name, String groupName, int priority, int id) {
        this.name = name;
        this.groupName = groupName;
        this.priority = priority;
        this.id = id;
    }

    public CategoryItem(String name, int priority) {
        this.name = name;
        this.groupName = "";
        this.priority = priority;
        id = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
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
