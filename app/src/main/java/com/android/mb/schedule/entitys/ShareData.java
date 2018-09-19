package com.android.mb.schedule.entitys;

import java.io.Serializable;
import java.util.List;

public class ShareData implements Serializable {

    private List<ShareBean> list;

    public List<ShareBean> getList() {
        return list;
    }

    public void setList(List<ShareBean> list) {
        this.list = list;
    }
}
