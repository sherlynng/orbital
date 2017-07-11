package com.example.btw.whatsup;

/**
 * Created by sherl on 4/7/2017.
 */

public class User {
    private String providerId;
    private String uid;
    private String name;
    private String email;

    public User(String providerId, String uid, String name, String email) {
        this.providerId = providerId;
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
