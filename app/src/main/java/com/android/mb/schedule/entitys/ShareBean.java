package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareBean implements Serializable{

    private long id;
    private String title;
    private String address;
    private String time;
    private String create_date;
    private List<UserBean> share;
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

    public String getCreate_date() {
        return create_date == null ? "" : create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public List<UserBean> getShare() {
        if (share == null) {
            return new ArrayList<>();
        }
        return share;
    }

    public void setShare(List<UserBean> share) {
        this.share = share;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }
}
