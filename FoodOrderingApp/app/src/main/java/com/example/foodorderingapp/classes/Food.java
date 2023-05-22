package com.example.foodorderingapp.classes;

public class Food {

    private int amount;
    private String cooktime;
    private String description;
    private int id ;
    private String imgsrc;
    private int kcal;
    private String name;
    private String unit;
    private int price;
    private int rvcount;
    private int svstart;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRvcount() {
        return rvcount;
    }

    public void setRvcount(int rvcount) {
        this.rvcount = rvcount;
    }

    public int getSvstart() {
        return svstart;
    }

    public void setSvstart(int svstart) {
        this.svstart = svstart;
    }

    public Food(int amount, String cooktime, String description, int id, String imgsrc, int kcal, String name, String unit, int price, int rvcount, int svstart) {
        this.amount = amount;
        this.cooktime = cooktime;
        this.description = description;
        this.id = id;
        this.imgsrc = imgsrc;
        this.kcal = kcal;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.rvcount = rvcount;
        this.svstart = svstart;
    }
}
