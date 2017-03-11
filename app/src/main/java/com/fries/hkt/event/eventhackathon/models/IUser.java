package com.fries.hkt.event.eventhackathon.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmq on 11/03/2017.
 */
@IgnoreExtraProperties
public class IUser {
    private String avatar;
    private String email;
    private String name;

    public IUser() {
    }

    public IUser(String avatar, String email, String name) {
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("avatar", avatar);
        result.put("email", email);
        result.put("name", name);
        return result;
    }
}
