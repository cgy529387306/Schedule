package com.android.mb.schedule.entitys;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
    private String username;
    private String nickname;
    private String mobile;
    private String eid;
    private long office_id;
    private String office_name;
    private String avatar;
    private String token;
    private long expiretime;
    private long token_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public long getOffice_id() {
        return office_id;
    }

    public void setOffice_id(long office_id) {
        this.office_id = office_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(long expiretime) {
        this.expiretime = expiretime;
    }

    public long getToken_id() {
        return token_id;
    }

    public void setToken_id(long token_id) {
        this.token_id = token_id;
    }
}
