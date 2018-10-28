package com.android.mb.schedule.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by cgy on 18/10/28.
 */
@Entity
public class User implements Serializable{
    private static final long serialVersionUID = 883137399;

    @Id(autoincrement = true)
    private Long id;
    private String eid;
    private long role_id;
    private String reg_id;
    private long office_id;
    private int user_type;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String mobile;
    private String email;
    private long loginfailure;
    private long logintime;
    private long createtime;
    private long updatetime;
    private long create_by;
    private long update_by;
    private String token;
    private String status;
    private String remarks;
    @Generated(hash = 883137399)
    public User(Long id, String eid, long role_id, String reg_id, long office_id,
            int user_type, String username, String nickname, String avatar,
            String phone, String mobile, String email, long loginfailure,
            long logintime, long createtime, long updatetime, long create_by,
            long update_by, String token, String status, String remarks) {
        this.id = id;
        this.eid = eid;
        this.role_id = role_id;
        this.reg_id = reg_id;
        this.office_id = office_id;
        this.user_type = user_type;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.loginfailure = loginfailure;
        this.logintime = logintime;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.create_by = create_by;
        this.update_by = update_by;
        this.token = token;
        this.status = status;
        this.remarks = remarks;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEid() {
        return this.eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    public long getRole_id() {
        return this.role_id;
    }
    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }
    public String getReg_id() {
        return this.reg_id;
    }
    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }
    public long getOffice_id() {
        return this.office_id;
    }
    public void setOffice_id(long office_id) {
        this.office_id = office_id;
    }
    public int getUser_type() {
        return this.user_type;
    }
    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getLoginfailure() {
        return this.loginfailure;
    }
    public void setLoginfailure(long loginfailure) {
        this.loginfailure = loginfailure;
    }
    public long getLogintime() {
        return this.logintime;
    }
    public void setLogintime(long logintime) {
        this.logintime = logintime;
    }
    public long getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
    public long getUpdatetime() {
        return this.updatetime;
    }
    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }
    public long getCreate_by() {
        return this.create_by;
    }
    public void setCreate_by(long create_by) {
        this.create_by = create_by;
    }
    public long getUpdate_by() {
        return this.update_by;
    }
    public void setUpdate_by(long update_by) {
        this.update_by = update_by;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRemarks() {
        return this.remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
