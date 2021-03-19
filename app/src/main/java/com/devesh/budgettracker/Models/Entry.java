package com.devesh.budgettracker.Models;

public class Entry {

    String desc;
    long date;
    int money ;
    String id;

    public Entry(){

    }

    public Entry(String des,long date,int mon,String id){
        this.date=date;
        this.money =mon;
        this.desc = des;
        this.id=id;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }



}
