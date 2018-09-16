package com.android.mb.schedule.widget.treeview.bean;

import com.android.mb.schedule.R;
import com.android.mb.schedule.widget.treeview.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class Dir implements LayoutItemType {
    public String dirName;

    public Dir(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }
}
