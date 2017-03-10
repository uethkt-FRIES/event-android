package com.fries.hkt.event.eventhackathon.models;

/**
 * Created by tmq on 11/03/2017.
 */


public class AgendaItem {

    public static final int STATE_PASSED = 0;
    public static final int STATE_RESPONDING = 1;
    public static final int STATE_WAITING = 2;


    public String info;
    public int state;

    public AgendaItem() {
        info = "";
    }

    public AgendaItem(String info) {
        this.info = info;
        this.state = 0;
    }

    public void setState(int state) {
        this.state = state;
    }

}
