package com.android.mb.schedule.entitys;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class TreeData implements Serializable{
    private TreeBean tree;

    public TreeBean getTree() {
        return tree;
    }

    public void setTree(TreeBean tree) {
        this.tree = tree;
    }
}
