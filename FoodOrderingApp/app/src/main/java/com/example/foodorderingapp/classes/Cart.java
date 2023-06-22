package com.example.foodorderingapp.classes;

public class Cart {
    String foodName;
    String quantity;
    String price;
    String rvStar;
    String rvCount;
    String foodImg;
    int totalPrice;
    String documentId;

    public Cart() {
    }

    public Cart(String foodName, String quantity, String price, String rvStar, String rvCount, String foodImg, int totalPrice) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.rvStar = rvStar;
        this.rvCount = rvCount;
        this.foodImg = foodImg;
        this.totalPrice = totalPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRvStar() {
        return rvStar;
    }

    public void setRvStar(String rvStar) {
        this.rvStar = rvStar;
    }

    public String getRvCount() {
        return rvCount;
    }

    public void setRvCount(String rvCount) {
        this.rvCount = rvCount;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}