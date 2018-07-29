package com.example.dong.contact;

public class Contact {

    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        this.mName = name;
        this.mPhone = phone;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getPhone() {
        return this.mPhone;
    }

}
