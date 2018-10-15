package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cgy on 18/10/15.
 */

public class UserData implements Serializable{
  private List<UserBean> userList;

    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }
}
