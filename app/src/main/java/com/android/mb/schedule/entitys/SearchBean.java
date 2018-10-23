package com.android.mb.schedule.entitys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgy on 18/10/23.
 */

public class SearchBean {
    private long id;
    private String name;
    private List<UserBean> list;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBean> getList() {
        return list==null?new ArrayList<UserBean>():list;
    }

    public void setList(List<UserBean> list) {
        this.list = list;
    }
}
