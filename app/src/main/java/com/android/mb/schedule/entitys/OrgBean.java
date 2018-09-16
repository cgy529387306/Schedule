package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\16 0016.
 */

public class OrgBean implements Serializable{
    private long id;

    private long parent_id;

    private String name;

    private List<OrgBean> childlist;

    private List<PersonBean> man;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrgBean> getChildlist() {
        if (childlist == null) {
            return new ArrayList<>();
        }
        return childlist;
    }

    public void setChildlist(List<OrgBean> childlist) {
        this.childlist = childlist;
    }

    public List<PersonBean> getMan() {
        if (man == null) {
            return new ArrayList<>();
        }
        return man;
    }

    public void setMan(List<PersonBean> man) {
        this.man = man;
    }
}
