package com.fries.hkt.event.eventhackathon.models;

import java.util.ArrayList;

/**
 * Created by tmq on 11/03/2017.
 */

public class IEvent {

    private ArrayList<String> banners;
    private boolean is_online;
    private String name;
    private String place;
    private ArrayList<IUser> users;

    public IEvent(){
        banners = new ArrayList<>();
        users = new ArrayList<>();
    }
}
