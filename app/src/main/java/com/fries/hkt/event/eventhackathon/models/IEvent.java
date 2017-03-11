package com.fries.hkt.event.eventhackathon.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmq on 11/03/2017.
 */

public class IEvent {

    private String key;
    private String banner;
    private boolean is_online;
    private String map;
    private String name;
    private String place;
    private String status;

    private List<ITimeLine> timelines;
    private List<IUser> users;

    public IEvent(){
    }

    public IEvent(String banner, boolean is_online, String map, String name, String place, String status, List<ITimeLine> timelines, List<IUser> users) {
        this.banner = banner;
        this.is_online = is_online;
        this.map = map;
        this.name = name;
        this.place = place;
        this.status = status;
        this.timelines = timelines;
        this.users = users;
    }
    public String getKey(){
        return key;
    }

    public String getBanner() {
        return banner;
    }

    public boolean getIs_online() {
        return is_online;
    }

    public String getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public List<ITimeLine> getTimelines() {
        return timelines;
    }

    public List<IUser> getUsers() {
        return users;
    }
}
