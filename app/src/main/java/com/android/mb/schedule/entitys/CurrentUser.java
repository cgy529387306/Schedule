package com.android.mb.schedule.entitys;

import android.util.Log;

import com.android.mb.schedule.utils.Helper;
import com.android.mb.schedule.utils.JsonHelper;
import com.android.mb.schedule.utils.PreferencesHelper;


/**
 * 作者：cgy on 16/12/26 22:53
 * 邮箱：593960111@qq.com
 */
public class CurrentUser {
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
    private long id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 优先显示nickname,没有显示username
     * @return
     */
    public String getNickname() {
        return Helper.isEmpty(nickname)?username:nickname;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //region 单例
    private static final String TAG = CurrentUser.class.getSimpleName();
    private static final String USER = "CurrentUser";
    private static CurrentUser me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static CurrentUser getInstance() {
        if (me == null) {
            me = new CurrentUser();
        }
        return me;
    }
    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean isLogin() {
        String json = PreferencesHelper.getInstance().getString(USER);
        me = JsonHelper.fromJson(json, CurrentUser.class);
        return me != null;
    }

    public boolean login(CurrentUser entity,boolean isLogin) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            me.setId(entity.getId());
            me.setUsername(entity.getUsername());
            me.setNickname(entity.getNickname());
            me.setMobile(entity.getMobile());
            me.setEid(entity.getEid());
            me.setOffice_id(entity.getOffice_id());
            me.setOffice_name(entity.getOffice_name());
            me.setAvatar(entity.getAvatar());
            if (isLogin){
                me.setToken(entity.getToken());
                me.setToken_id(entity.getToken_id());
                me.setExpiretime(entity.getExpiretime());
            }
            json = JsonHelper.toJson(me);
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(USER,json);
        }
        return born;
    }

    // endregion 单例

    /**
     * 退出登录清理用户信息
     */
    public void loginOut() {
        me = null;
        PreferencesHelper.getInstance().putString(USER, "");
    }
}
