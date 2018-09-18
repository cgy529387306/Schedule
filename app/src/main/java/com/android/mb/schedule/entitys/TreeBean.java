package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class TreeBean implements Serializable{
    private long id;
    private String name;
    private long parent_id;
    private List<TreeBean> childlist;
    private List<UserBean> man;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public List<TreeBean> getChildlist() {
        if (childlist == null) {
            return new ArrayList<>();
        }
        return childlist;
    }

    public void setChildlist(List<TreeBean> childlist) {
        this.childlist = childlist;
    }

    public List<UserBean> getMan() {
        if (man == null) {
            return new ArrayList<>();
        }
        return man;
    }

    public void setMan(List<UserBean> man) {
        this.man = man;
    }
}
