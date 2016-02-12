package com.lunokhod.java.android.cashflowapp;

import java.util.Date;

/**
 * Created by alex on 09.02.2016.
 */
public class ChargeRecord {
    public static final int DEBET = 0;
    public static final int CREDIT = 1;

    private float amount;
    private CategoryItem category;
    private Date date;
    private int credit;
    private String comment;
    private int account;
    private int id;

    public ChargeRecord(float amount, CategoryItem category, Date date) {
        this.amount = amount;
        this.category = category;
        this.comment = "";
        this.date = date;
        this.credit = DEBET;
        account = 0;
        id = 0;
    }

    public ChargeRecord(float amount, CategoryItem category, String comment, Date date) {
        this.amount = amount;
        this.category = category;
        this.comment = comment;
        this.date = date;
        this.credit = DEBET;
        account = 0;
        id = 0;
    }

    public ChargeRecord(float amount, CategoryItem category, String comment, Date date, int credit, int account, int id) {
        this.amount = amount;
        this.category = category;
        this.comment = comment;
        this.date = date;
        this.credit = credit;
        this.account = account;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public CategoryItem getCategory() {
        return category;
    }

    public void setCategory(CategoryItem category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }
}
