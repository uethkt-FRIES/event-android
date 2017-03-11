package com.fries.hkt.event.eventhackathon.models;

import java.util.ArrayList;

/**
 * Created by tmq on 11/03/2017.
 */

public class ITimeLine {

    private long end_time;
    ArrayList<IFeedback> feedback;
    private boolean is_online;
    ArrayList<IKhachMoi> khachmoi;
    private String name;
    private String place;
    ArrayList<IPool> pool;
    ArrayList<IPoolUser> pool_users;
    ArrayList<IQuestion> question;
    private long start_time;

    public ITimeLine(){}

    public ITimeLine(long end_time, ArrayList<IFeedback> feedback, boolean is_online, ArrayList<IKhachMoi> khachmoi, String name, String place, ArrayList<IPool> pool, ArrayList<IPoolUser> pool_users, ArrayList<IQuestion> question, long start_time) {
        this.end_time = end_time;
        this.feedback = feedback;
        this.is_online = is_online;
        this.khachmoi = khachmoi;
        this.name = name;
        this.place = place;
        this.pool = pool;
        this.pool_users = pool_users;
        this.question = question;
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public ArrayList<IFeedback> getFeedback() {
        return feedback;
    }

    public boolean is_online() {
        return is_online;
    }

    public ArrayList<IKhachMoi> getKhachmoi() {
        return khachmoi;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public ArrayList<IPool> getPool() {
        return pool;
    }

    public ArrayList<IPoolUser> getPool_users() {
        return pool_users;
    }

    public ArrayList<IQuestion> getQuestion() {
        return question;
    }

    public long getStart_time() {
        return start_time;
    }

    // ------------------------------- Class -------------------------------------------------------
    public static class IFeedback {
        private String content;
        private String email;

        public IFeedback(){}

        public IFeedback(String content, String email) {
            this.content = content;
            this.email = email;
        }

        public String getContent() {
            return content;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class IKhachMoi {
        private String key;
        private String email;
        private String name;

        public IKhachMoi(){}

        public IKhachMoi(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

    }

    public static class IPool {
        private ArrayList<String> choices;
        private String title;

        public IPool() {
            choices = new ArrayList<>();
        }

        public ArrayList<String> getChoices() {
            return choices;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class IPoolUser {
        private int choice;
        private long pool_id;
        private IUser user;

        public IPoolUser(){}

        public IPoolUser(int choice, long pool_id, IUser user) {
            this.choice = choice;
            this.pool_id = pool_id;
            this.user = user;
        }

        public int getChoice() {
            return choice;
        }

        public long getPool_id() {
            return pool_id;
        }

        public IUser getUser() {
            return user;
        }
    }

    public static class IQuestion {
        private String content;
        private String email;

        public IQuestion(){}

        public IQuestion(String content, String email) {
            this.content = content;
            this.email = email;
        }


        public String getContent() {
            return content;
        }

        public String getEmail() {
            return email;
        }
    }


}
