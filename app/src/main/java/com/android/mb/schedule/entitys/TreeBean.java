package com.android.mb.schedule.entitys;

import com.android.mb.schedule.R;
import com.android.mb.schedule.widget.treeview.LayoutItemType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class TreeBean implements Serializable,LayoutItemType {
    private long id;
    private String name;
    private long parent_id;
    private List<TreeBean> childlist;
    private List<UserBean> man;
    private int levelCnt;//当前层级
    /** 是否展开了 */
    private boolean isExpand;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    public int getChildCount() {
        if (childlist == null) {
            return 0;
        } else {
            return childlist.size();
        }
    }

    public int getLevelCnt() {
        return levelCnt;
    }

    public void setLevelCnt(int levelCnt) {
        this.levelCnt = levelCnt;
    }

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

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_org;
    }
}
