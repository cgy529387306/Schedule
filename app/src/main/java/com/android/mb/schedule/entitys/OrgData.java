package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\16 0016.
 */

public class OrgData implements Serializable{
    private List<OrgBean> tree;

    public List<OrgBean> getTree() {
        if (tree == null) {
            return new ArrayList<>();
        }
        return tree;
    }

    public void setTree(List<OrgBean> tree) {
        this.tree = tree;
    }
}
