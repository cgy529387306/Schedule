package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class TreeData implements Serializable{
    private List<TreeBean> tree;

    public List<TreeBean> getTree() {
        return tree;
    }

    public void setTree(List<TreeBean> tree) {
        this.tree = tree;
    }
}
