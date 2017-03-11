package com.fries.hkt.event.eventhackathon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fries.hkt.event.eventhackathon.models.IUser;

/**
 * Created by tmq on 11/03/2017.
 */

public class SharedPreferencesMgr {
    private static final String USER = "com.fries.hkt.event.eventhackathon.USER";
    private static final String APP = "com.fries.hkt.event.eventhackathon.APP";
    private static final String EVENT = "com.fries.hkt.event.eventhackathon.EVENT";

    private Context mContext;

    public SharedPreferencesMgr(Context context) {
        this.mContext = context;
    }

    public void setLogin(boolean isLoggedIn) {
        SharedPreferences preferences = mContext.getSharedPreferences(APP, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_login", isLoggedIn);

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences preferences = mContext.getSharedPreferences(APP, Context.MODE_PRIVATE);
        return preferences.getBoolean("is_login", false);
    }

    public void setUserInfo(IUser user) {
        SharedPreferences preferences = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("avatar", user.getAvatar());
        editor.putString("email", user.getEmail());
        editor.putString("name", user.getName());

        editor.apply();
    }

    public IUser getUserInfo() {
        SharedPreferences preferences = mContext.getSharedPreferences(USER, Context.MODE_PRIVATE);

        String avatar = preferences.getString("avatar", "");
        String email = preferences.getString("email", "");
        String name = preferences.getString("name", "");

        return new IUser(avatar, email, name);
    }

    public void setEventId(String id) {
        SharedPreferences preferences = mContext.getSharedPreferences(EVENT, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);

        editor.apply();
    }

    public String getEventId() {
        SharedPreferences preferences = mContext.getSharedPreferences(EVENT, Context.MODE_PRIVATE);

        return preferences.getString("id", "");
    }
}
