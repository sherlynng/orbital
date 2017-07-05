package com.example.btw.whatsup;

/**
 * Created by User on 2/8/2017.
 */

public class UserInformation {


    private String name;
    private String email;
   // private String phone_num;

    public UserInformation(){

    }

    public UserInformation(String email, String name) {
        this.email = email;
        this.name = name;
 //       this.phone_num = phone_num;
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

}
