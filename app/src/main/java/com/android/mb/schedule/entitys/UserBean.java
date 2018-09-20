package com.android.mb.schedule.entitys;

import com.android.mb.schedule.R;
import com.android.mb.schedule.widget.treeview.LayoutItemType;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class UserBean implements Serializable,LayoutItemType{
    private long id;
    private String nickname;
    private long office_id;
    private String avatar;
    private boolean isSelect;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname == null ? "" : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getOffice_id() {
        return office_id;
    }

    public void setOffice_id(long office_id) {
        this.office_id = office_id;
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_user;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
