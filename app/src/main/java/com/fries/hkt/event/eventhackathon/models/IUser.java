package com.fries.hkt.event.eventhackathon.models;

/**
 * Created by tmq on 11/03/2017.
 */

public class IUser {
    private String avatar;
    private String email;
    private String name;

    public IUser(String avatar, String email, String name){
        this.avatar = avatar;
        this.email = email;
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
