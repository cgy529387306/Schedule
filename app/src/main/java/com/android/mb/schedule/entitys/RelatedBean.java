package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\19 0019.
 */

public class RelatedBean implements Serializable{

    private long id;
    private String title;
    private String address;
    private String time;
    private String create_date;
    private String user_avatar;
    private String user_name;
    private String user_office;
    private int important;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String
    getCreate_date() {
        return create_date == null ? "" : create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUser_avatar() {
        return user_avatar == null ? "" : user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_name() {
        return user_name == null ? "" : user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_office() {
        return user_office == null ? "" : user_office;
    }

    public void setUser_office(String user_office) {
        this.user_office = user_office;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }
}
