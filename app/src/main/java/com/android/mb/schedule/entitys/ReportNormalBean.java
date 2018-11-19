package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by cgy on 18/11/18.
 */

public class ReportNormalBean implements Serializable{

    /**
     * office_name : 马尾造船
     * plan_num : 0
     * used_time : 0
     * nickname : 蔡桂有
     * id : 2005
     * uid : 2005
     * username : 18650480850
     * is_admin : 0
     * s_time : 2018-11-05
     * e_time : 2018-11-11
     * used_num : 0
     */

    private String office_name;
    private int plan_num;
    private long used_time;
    private String nickname;
    private String id;
    private String uid;
    private String username;
    private int is_admin;
    private String s_time;
    private String e_time;
    private int used_num;

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public int getPlan_num() {
        return plan_num;
    }

    public void setPlan_num(int plan_num) {
        this.plan_num = plan_num;
    }

    public long getUsed_time() {
        return used_time;
    }

    public void setUsed_time(long used_time) {
        this.used_time = used_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public int getUsed_num() {
        return used_num;
    }

    public void setUsed_num(int used_num) {
        this.used_num = used_num;
    }
}
