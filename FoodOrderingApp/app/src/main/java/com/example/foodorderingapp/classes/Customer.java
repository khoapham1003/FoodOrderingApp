package com.example.foodorderingapp.classes;

public class Customer {
    private int ID;
    private String name;
    private String sex;
    private String dob;
    private String email;
    private String phonenum;
    private String nationality;
    private String username;
    private String password;
    private String imgsrc;

    public Customer() {
    }

    public Customer(int ID, String name, String sex, String dob, String email, String phonenum, String nationality, String username, String password, String imgsrc) {
        this.ID = ID;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phonenum = phonenum;
        this.nationality = nationality;
        this.username = username;
        this.password = password;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

//    public void uploadCustomerDatabase(Customer cus)
//    {
//        Map<int,String,String,String,String,String,String,String,String,String>
//    }


}
