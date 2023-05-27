package com.example.foodorderingapp.classes;

public class Food {
    private int ID;
    private String name;
    private double price ;
    private int rvcount;
    private double rvstar;
    private int amount;
    private int kcal;
    private String unit;
    private String cooktime;
    private String description;
    private String imgsrc;

    public Food() {
    }
    public Food(int ID, String name, double price, int rvcount, double rvstar, int amount, int kcal, String unit, String cooktime, String description, String imgsrc) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.rvcount = rvcount;
        this.rvstar = rvstar;
        this.amount = amount;
        this.kcal = kcal;
        this.unit = unit;
        this.cooktime = cooktime;
        this.description = description;
        this.imgsrc = imgsrc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRvcount() {
        return rvcount;
    }

    public void setRvcount(int rvcount) {
        this.rvcount = rvcount;
    }

    public double getRvstar() {
        return rvstar;
    }

    public void setRvstar(double rvstar) {
        this.rvstar = rvstar;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCooktime() {
        return cooktime;
    }

    public void setCooktime(String cooktime) {
        this.cooktime = cooktime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
