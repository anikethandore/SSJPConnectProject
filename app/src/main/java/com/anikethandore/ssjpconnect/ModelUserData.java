package com.anikethandore.ssjpconnect;

public class ModelUserData {

    String email,name,phoneNo,uid;
    public ModelUserData() {
    }

    public ModelUserData(String email, String name, String phoneNo, String uid) {
        this.email = email;
        this.name = name;
        this.phoneNo = phoneNo;
        this.uid = uid;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
