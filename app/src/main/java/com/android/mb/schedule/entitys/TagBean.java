package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cgy on 18/11/5.
 */

public class TagBean implements Serializable{
    private long id;
    private String name;
    private List<UserBean> man;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBean> getMan() {
        return man;
    }

    public void setMan(List<UserBean> man) {
        this.man = man;
    }
}
