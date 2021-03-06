package com.fries.hkt.event.eventhackathon.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tmq on 11/03/2017.
 */

public class ITimeLine {

    private String description;
    private long end_time;
//    private ArrayList<IFeedback> feedback;
    private boolean is_online;
    private boolean enabled;
    private ArrayList<IKhachMoi> khachmoi;
    private String name;
    private String place;
    private ArrayList<IPool> pool;
    private ArrayList<IPoolUser> pool_users;
    private ArrayList<IQuestion> question;
    private String related;
    private long start_time;

    public ITimeLine(){}

    public String getDescription() {
        return description;
    }

    public long getEnd_time() {
        return end_time;
    }

//    public ArrayList<IFeedback> getFeedback() {
//        return feedback;
//    }

    public boolean getIs_online() {
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

    public boolean getEnabled() {
        return enabled;
    }

    public String getRelated() {
        return related;
    }

    // ------------------------------- Class -------------------------------------------------------
    public static class IFeedback {
        private String content;
        private String email;
        private long star;

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

        public long getStar() {
            return star;
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
