package com.fries.hkt.event.eventhackathon.models;

import java.util.ArrayList;

/**
 * Created by tmq on 11/03/2017.
 */

public class ITimeLine {

    private String start_time;
    private String end_time;
    private boolean is_online;
    private String name;
    private String place;

    ArrayList<IFeedback> feedback;
    ArrayList<IKhachMoi> khachmoi;
    ArrayList<IPool> pool;
    ArrayList<IPoolUser> pool_users;
    ArrayList<IQuestion> question;


    // ------------------------------- Class -------------------------------------------------------
    public class IFeedback {
        String content;
        String email;
    }

    public class IKhachMoi {
        String email;
        String name;
    }

    public class IPool {
        String title;
        ArrayList<String> choices;

        public IPool() {
            choices = new ArrayList<>();
        }
    }

    public class IPoolUser {
        int choice;
        long pool_id;
        IUser user;
    }

    public class IQuestion {
        String content;
        String email;
    }


}
